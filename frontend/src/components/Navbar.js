import React from 'react';
import { Link, useNavigate, useLocation } from 'react-router-dom';

function Navbar() {
  const navigate = useNavigate();
  const user = JSON.parse(localStorage.getItem('user'));
  const location = useLocation();

  const handleLogout = () => {
    localStorage.removeItem('user');
    navigate('/');
    window.location.reload();
  };

  const isRouteActive = (routePath) => {
    return location.pathname.startsWith(routePath);
  };

  const activeItemStyle = {
    fontWeight: 'bold',
  };

  const itemSpacing = {
    marginRight: '20px',
  };

  return (
    <nav className="navbar navbar-expand-lg navbar-light bg-light">
      <div className="container">
        <div className="collapse navbar-collapse" id="navbarNav">
          <ul className="navbar-nav mx-auto">
            <li className="nav-item" style={{ ...itemSpacing, ...(isRouteActive('/home') ? activeItemStyle : {}) }}>
              <Link to="/home" className="nav-link">
                Store Home
              </Link>
            </li>
            <li className="nav-item" style={{ ...itemSpacing, ...(isRouteActive('/incidents') ? activeItemStyle : {}) }}>
              <Link to="/incidents" className="nav-link">
                Incidents
              </Link>
            </li>
            {user && user.roles && user.roles.includes('ROLE_MANAGER') && (
              <>
                <li className="nav-item" style={{ ...itemSpacing, ...(isRouteActive('/user') ? activeItemStyle : {}) }}>
                  <Link to="/user" className="nav-link">
                    User
                  </Link>
                </li>
              </>
            )}
          </ul>
          <button className="nav-link" onClick={handleLogout}>
            Logout
          </button>
        </div>
      </div>
    </nav>
  );
}

export default Navbar;
