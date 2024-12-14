import { ReactNode } from "react";

interface Props {
  children: ReactNode;
  className?: string;
  color?: string;
}

function CardComponent({children, className, color="white"}: Props) {
  return <div className={`rounded box ${className}`} style={{ background: color}}>
    {children}
  </div>
}

export default CardComponent;