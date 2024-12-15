import { ReactNode } from "react";
import "./slist.css";

interface Param {
  children?: ReactNode
}

function ScrollableList({children}: Param) {
  return (
    <div className="scrollable-box">
      {children}
    </div>
  );
};

export default ScrollableList;