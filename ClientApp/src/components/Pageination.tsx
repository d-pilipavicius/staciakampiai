interface Param {
  selectedPage: number;
  totalPages: number;
  setPage: (id: number) => void;
}

function Pageination({selectedPage, totalPages, setPage}: Param) {
  const items: JSX.Element[] = [];
  for(let i = 0; i < totalPages; ++i) {
    items.push(<a key={`page${i+1}`} onClick={() => setPage(i+1)} className={selectedPage === i+1 ? "selected" : ""}>{i+1}</a>);
  }
  
  return <div className="pageination">
    {items}
  </div>
}

export default Pageination;