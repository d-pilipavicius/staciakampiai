import { ReactNode } from "react";

interface Props {
  children: ReactNode;
  className?: string;
}

function CardComponent({children, className}: Props) {
  return <div className={`rounded box ${className}`}>
    {children}
  </div>
}

export default CardComponent;