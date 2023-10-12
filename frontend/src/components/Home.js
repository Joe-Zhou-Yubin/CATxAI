import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link, useNavigate } from 'react-router-dom';

function Home() {
  const [stores, setStores] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    // Fetch the list of stores from the API
    axios.get('http://localhost:8080/api/stores/list')
      .then((response) => {
        setStores(response.data);
      })
      .catch((error) => {
        console.error('Error fetching stores:', error);
      });
  }, []); // Empty dependency array to run the effect only once

  const handleCreateStoreClick = () => {
    // Navigate to the "/createstore" route
    navigate('/createstore');
  };

  return (
    <div className="container mt-5">
      <div className="d-flex justify-content-between align-items-center">
        <h2>Stores</h2>
        <button
          className="btn btn-primary"
          onClick={handleCreateStoreClick}
        >
          Create Store
        </button>
      </div>
      <div className="row">
        {stores.map((store) => (
          <div key={store.id} className="col-md-4 mb-4">
            <Link to={`/store/${store.storeId}`} style={{ textDecoration: 'none' }}>
              <div className="card">
                <div className="card-body">
                  <h5 className="card-title">Store Info</h5>
                  <p className="card-text">Store ID: {store.storeId}</p>
                  <p className="card-text">Postcode: {store.postcode}</p>
                  <p className="card-text">Population: {store.population}</p>
                  <p className="card-text">Threshold Amount: {store.thresamount}</p>
                  <p className="card-text">Minimum Amount: {store.minamount}</p>
                </div>
              </div>
            </Link>
          </div>
        ))}
      </div>
    </div>
  );
}

export default Home;
