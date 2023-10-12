import React from 'react';
import { render } from 'react-dom';
import { BrowserRouter as Router, Routes, Route, useNavigate } from 'react-router-dom';

import Login from './components/Login';
import User from './components/User';
import Navbar from './components/Navbar';
import Months from './components/Months';
import Incident from './components/Incident';
import Home from './components/Home';
import Store from './components/Store';
import CreateUser from './components/CreateUser';
import CreateStore from './components/CreateStore';
import CreateMonth from './components/CreateMonth';
import CreateIncident from './components/CreateIncident';

import 'bootstrap/dist/css/bootstrap.min.css';

function App(){
  // Define an array of paths where you want the Navbar to be rendered
  const navbarPaths = [
    '/home',
    '/user',
    '/createuser',
    '/store/',          
    '/month/',         
    '/incidents',       
    '/createstore',    
    '/createmonth',     
    '/createincident',  
  ];
    const user = JSON.parse(localStorage.getItem('user'));
  // Check if the current path is in the array of navbarPaths
  const shouldRenderNavbar = navbarPaths.some((path) => window.location.pathname.startsWith(path));

  return(
    <Router>
      {shouldRenderNavbar && <Navbar />} {/* Conditional rendering of Navbar */}
      <Routes>
        <Route path="/*" element={<Login />} />
        <Route path="/home" element={<Home />} />
        <Route path="/store/:storeId" element={<Store />} />
        <Route path="/month/:monthId" element={<Months />} />
        <Route path="/incidents" element={<Incident />} />
        <Route path="/createstore" element={<CreateStore />} />
        <Route path="/createmonth/:storeId" element={<CreateMonth />} />
        <Route path="/createincident" element={<CreateIncident />} />
        {user && user.roles && user.roles.includes('ROLE_MANAGER') && (
          <>
            <Route path="/user" element={<User />} />
            <Route path="/createuser" element={<CreateUser />} />
          </>
        )}
      </Routes>
    </Router>
  );
}

render(<App />, document.getElementById('root'));