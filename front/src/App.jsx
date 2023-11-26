import { useEffect, useRef, useState } from "react";
import { Editor } from "@monaco-editor/react";
import Tree from "react-d3-tree";
import "./App.css";

function App() {
  const editorRef = useRef(null);
  const [tree, setTree] = useState(null);
  const [editorValue, setEditorValue] = useState(null);
  const [file, setFile] = useState(null);

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
      <div className="main">
        <header className="header">
          <button className="btn btn-dark">Compilar</button>
          <input type="file" className="btn btn-dark" />
        </header>
        <main className="txt-editor">
          {/* <Editor
            width="100%"
            theme="vs-dark"
            path="filename"
            onChange={handleEditorChange}
            onMount={handleEditorDidMount}
            value={editorValue}
          /> */}
        </main>
      </div>
    </div>

    // <div
    //   style={{
    //     display: "flex",
    //     flexDirection: "row",
    //   }}
    // >
    //   <div
    //     style={{
    //       width: "70%",
    //       height: "100vh",
    //     }}
    //   >
    //     <button onClick={() => compile(editorValue)}>Compilar</button>
    //     <input type="file" onInput={handleFileChange} />

    //     <Editor
    //       height="100%"
    //       width="100%"
    //       theme="vs-dark"
    //       path="filename"
    //       onChange={handleEditorChange}
    //       onMount={handleEditorDidMount}
    //       value={editorValue}
    //     />
    //   </div>
    //   <div style={{ width: "30%", height: "100vh" }}>
    //     {tree && <Tree data={tree} orientation="horizontal" />}
    //   </div>
    // </div>
  );
}

export default App;
