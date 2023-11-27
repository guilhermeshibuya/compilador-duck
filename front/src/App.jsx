import { useEffect, useRef, useState } from "react";
import { Editor } from "@monaco-editor/react";
import Tree from "react-d3-tree";
import playArrowIcon from "./assets/img/icons/play_arrow.svg";
import "./App.css";

function App() {
  const editorRef = useRef(null);
  const [tree, setTree] = useState(null);
  const [editorValue, setEditorValue] = useState(null);
  const [file, setFile] = useState(null);
  const [errorMsg, setErrorMsg] = useState("");

  function handleEditorDidMount(editor, monaco) {
    editorRef.current = editor;
  }

  function handleEditorChange(value, event) {
    setEditorValue(value);
  }

  const handleFileChange = (event) => {
    if (event.target.files) {
      setFile(event.target.files[0]);
    }
  };

  const compile = async (sourceCode) => {
    await fetch("http://localhost:8080/compiler/compile", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: sourceCode,
    })
      .then((response) => response.json())
      .then((data) => {
        setTree(data);
        setErrorMsg("");
      })
      .catch((e) => {
        console.log(e);
        setErrorMsg(e);
      });
  };

  useEffect(() => {
    if (file) {
      let fileReader = new FileReader();
      fileReader.onload = async (e) => {
        setEditorValue(e.target.result);
      };
      fileReader.readAsText(file);
    }
  }, [file]);

  return (
    <div className="container">
      <header className="header">
        <button className="btn btn-dark" onClick={() => compile(editorValue)}>
          Compilar
          <img src={playArrowIcon} />
        </button>
        <input
          type="file"
          className="btn btn-dark"
          onChange={handleFileChange}
        />
      </header>
      <div className="tree">
        {tree && (
          <Tree
            data={tree}
            rootNodeClassName="node__root"
            branchNodeClassName="node__branch"
            leafNodeClassName="node__leaf"
            orientation="horizontal"
            pathClassFunc={() => "custom-tree__link "}
          />
        )}
      </div>

      <main className="txt-editor">
        <Editor
          width="100%"
          height="100%"
          theme="vs-dark"
          path="filename"
          onChange={handleEditorChange}
          onMount={handleEditorDidMount}
          value={editorValue}
        />
      </main>
      <div className="error">
        <p>{errorMsg.toString()}</p>
      </div>
    </div>
  );
}

export default App;
