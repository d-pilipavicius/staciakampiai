import Popup from "./Popup";

interface Param {
  setVisibility: boolean;
  question: string;
  onAccept: () => void;
  onCancel: () => void;
}

function DialogBox({setVisibility, question, onAccept, onCancel}: Param) {
  return <Popup setVisibility={setVisibility}>
    <h3>{question}</h3>
    <button type="button" onClick={onAccept} className="btn btn-success dialog">Ok</button>
    <button type="button" onClick={onCancel} className="btn btn-danger dialog">Cancel</button>
  </Popup>
}

export default DialogBox;