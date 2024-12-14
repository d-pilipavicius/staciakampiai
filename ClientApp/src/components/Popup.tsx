import { ReactNode } from 'react';
import './popup.css'

interface Param {
  setVisibility: boolean
  children: ReactNode
  backgroundClass?: string
}

function Popup({setVisibility, children}: Param) {
  return (
    setVisibility &&
    <div>
      <div className="popupBackground"/>
      <div className="popup">
        {children}
      </div>
    </div>
  );
  
}

export default Popup;