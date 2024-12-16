import { ReactNode } from 'react';
import './popup.css'

interface Param {
  setVisibility: boolean;
  children: ReactNode;
  priority?: number;
}


//High priority = it shows up on top (only change if multiple popups occur, increment by 1 for each popup)
function Popup({setVisibility, children, priority}: Param) {
  return (
    setVisibility &&
    <div>
      <div className="popupBackground" style={{zIndex: 1040+(priority ?? 0)*11}}/>
      <div className="popup" style={{zIndex: 1050+(priority ?? 0)*11}}>
        {children}
      </div>
    </div>
  );
  
}

export default Popup;