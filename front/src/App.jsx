import { useEffect, useRef, useState } from "react";
import { Editor } from "@monaco-editor/react";
import Tree from "react-d3-tree";
import playArrowIcon from "./assets/img/icons/play_arrow.svg";
import fileOpenIcon from "./assets/img/icons/file_open.svg";
import fileSaveIcon from "./assets/img/icons/file_save.svg";
import newFileIcon from "./assets/img/icons/add.svg";
import "./App.css";

function App() {
  const editorRef = useRef(null);
  const [tree, setTree] = useState(null);
  const [editorValue, setEditorValue] = useState(null);
  const [files, setFiles] = useState([]);
  const [selectedFileIndex, setSelectedFileIndex] = useState(null);
  const [errorMsg, setErrorMsg] = useState([]);
  const [successMsg, setSuccessMsg] = useState(null);
  const [alertMsg, setAlertMsg] = useState(null);

  function handleEditorDidMount(editor, monaco) {
    editorRef.current = editor;
  }

  function handleEditorChange(value, event) {
    setEditorValue(value);
  }

  const handleFileChange = (event) => {
    if (event.target.files) {
      const newFiles = Array.from(event.target.files);
      setFiles([...files, ...newFiles]);
      if (files.length > 0) {
        setSelectedFileIndex(files.length - 1);
      } else {
        setSelectedFileIndex(0);
      }
    }
  };

  const createFile = () => {
    const blob = new Blob([], { type: "text/plain" });

    setFiles([...files, blob]);
    setSelectedFileIndex(files.length);
  };

  const handleFileDownload = () => {
    if (selectedFileIndex !== null && selectedFileIndex < files.length) {
      const file = files[selectedFileIndex];
      const blob = new Blob([editorValue], { type: "text/plain" });
      const url = URL.createObjectURL(blob);
      const link = document.createElement("a");
      link.href = url;
      link.download = file.name;
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
      URL.revokeObjectURL(url);
    }
  };

  const handleCloseFile = (index) => {
    if (selectedFileIndex !== null && selectedFileIndex < files.length) {
      const newFiles = [...files];
      newFiles.splice(index, 1);

      if (newFiles.length > 0) {
        if (selectedFileIndex >= newFiles.length) {
          setSelectedFileIndex(newFiles.length - 1);
        }
      } else {
        setSelectedFileIndex(null);
      }
      setFiles(newFiles);
    }
  };

  const compile = async (sourceCode) => {
    if (selectedFileIndex !== null && selectedFileIndex < files.length) {
      const response = await fetch("http://localhost:8080/compiler/compile", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: sourceCode,
      });
      if (response.ok) {
        const result = await response.json();
        setTree(result.tree);
        console.log(result);
        if (result.errors.length == 0) {
          setSuccessMsg("Compilado com sucesso!");
          setErrorMsg([]);
          setAlertMsg(null);
        } else {
          setAlertMsg("Compilado utilizando a recuperação de erro por pânico!");
          setErrorMsg(result.errors);
          setSuccessMsg(null);
        }
      } else {
        const error = await response.json();
        console.log(error);
        setErrorMsg(error.error);
      }
    }
  };

  useEffect(() => {
    if (
      files.length > 0 &&
      selectedFileIndex !== null &&
      selectedFileIndex < files.length
    ) {
      let fileReader = new FileReader();
      fileReader.onload = async (e) => {
        setEditorValue(e.target.result);
      };
      fileReader.readAsText(files[files.length - 1]);
    }
  }, [files, selectedFileIndex]);

  return (
    <div className="container">
      <div className="sidebar">
        <button className="btn btn-dark" onClick={() => compile(editorValue)}>
          <img src={playArrowIcon} />
          <span className="tooltip">Compilar código</span>
        </button>

        <button className="btn btn-dark" onClick={createFile}>
          <img src={newFileIcon} />
          <span className="tooltip">Novo arquivo</span>
        </button>

        <label htmlFor="file-upload" className="btn btn-dark">
          <img src={fileOpenIcon} />
          <span className="tooltip">Abrir arquivo</span>
        </label>
        <input id="file-upload" type="file" onChange={handleFileChange} />

        <button className="btn btn-dark" onClick={handleFileDownload}>
          <img src={fileSaveIcon} />
          <span className="tooltip">Salvar arquivo</span>
        </button>
      </div>
      <header className="header">
        {files &&
          files.map((file, index) => (
            <button
              key={index}
              className={`btn-page ${
                index === selectedFileIndex ? "btn-page-active" : ""
              }`}
              onClick={() => {
                setSelectedFileIndex(index);
                setEditorValue(file[index]);
              }}
            >
              {file.name}
              <div className="close" onClick={() => handleCloseFile(index)}>
                x
              </div>
            </button>
          ))}
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
        {files.length > 0 && selectedFileIndex !== null ? (
          <Editor
            width="100%"
            height="100%"
            theme="vs-dark"
            path={files[selectedFileIndex]}
            onChange={handleEditorChange}
            onMount={handleEditorDidMount}
            value={editorValue}
          />
        ) : (
          <div
            style={{
              width: "100%",
              height: "100%",
              backgroundColor: "#1e1e1e",
            }}
          ></div>
        )}
      </main>
      <div className="console">
        {alertMsg && <p className="console-txt alert-txt">{alertMsg}</p>}
        {successMsg && <p className="console-txt success-txt">{successMsg}</p>}
        {errorMsg.length > 0 &&
          errorMsg.map((error) => {
            return <p className="console-txt error-txt">{error}</p>;
          })}
      </div>
    </div>
  );
}

export default App;
