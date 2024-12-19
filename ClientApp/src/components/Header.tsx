function Header() {
  const names=["Business", "Products", "Orders", "Tax", "Discount", "Service Charge", "Payment", "Reservations"];
  const links=["/business", "/products", "/orders", "/tax", "/discounts", "/service-charges", "/payments", "/reservations"];

  return <>
      <header className="header">
        <a href={"/home"}>PoS system</a>
        <nav className="navbar">
          <ul>
            { names.map((name, index) => {
              return <li key={"link"+index}><a href={links[index]}>{name}</a></li>
            })}
          </ul>
        </nav>
        <div/>
      </header>
    </>;
}

export default Header;