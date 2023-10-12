import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams, useNavigate } from 'react-router-dom';

function Store() {
  const { storeId } = useParams();
  const [store, setStore] = useState({});
  const [riceData, setRiceData] = useState([]);
  const [incidents, setIncidents] = useState([]);
  const navigate = useNavigate();

  const handleDelete = () => {
    // Confirm the delete action with the user (you can use a confirmation dialog)
    if (window.confirm('Are you sure you want to delete this store?')) {
      // Send a delete request to the server
      axios.delete(`http://localhost:8080/api/stores/delete/${store.id}`)
        .then(() => {
          // Display a success message and navigate back to the home page
          alert('Store deleted successfully');
          navigate('/home'); // Navigate to the home page
        })
        .catch((error) => {
          console.error('Error deleting store:', error);
        });
    }
  };

  useEffect(() => {
    // Fetch the store by storeId from the API
    axios.get(`http://localhost:8080/api/stores/getByStoreId/${storeId}`)
      .then((response) => {
        setStore(response.data);
      })
      .catch((error) => {
        console.error('Error fetching store:', error);
      });

    // Fetch rice data for the specific store
    axios.get(`http://localhost:8080/api/rice/getbystore/${storeId}`)
      .then((response) => {
        setRiceData(response.data);
      })
      .catch((error) => {
        console.error('Error fetching rice data:', error);
      });

      axios.get(`http://localhost:8080/api/inci/getbystore/${storeId}`)
      .then((response) => {
        setIncidents(response.data);
      })
      .catch((error) => {
        console.error('Error fetching incidents:', error);
      });
  }, [storeId]);

  const handleCreateRecord = () => {
    navigate(`/createmonth/${storeId}`); // Redirect to /createmonth/{storeId}
  };
  const formatDate = (date) => {
    // Extract the "yyyy-mm" portion of the date string
    return date.slice(0, 7);
  };

  const handleCreatePrediction = (riceId) => {
    axios.post(`http://localhost:8080/api/pre/createpre/${riceId}`)
      .then(() => {
        // Display a success message if needed
        alert('Prediction created successfully');
        window.location.reload();
      })
      .catch((error) => {
        console.error('Error creating prediction:', error);
      });
  };

  const handleDeleteRice = (riceId) => {
    // Confirm the delete action with the user (you can use a confirmation dialog)
    if (window.confirm('Are you sure you want to delete this rice entry?')) {
      // Send a delete request to the server
      axios.delete(`http://localhost:8080/api/rice/delete/${riceId}`)
        .then(() => {
          // Display a success message if needed
          alert('Rice entry deleted successfully');
          // Refresh rice data or take other necessary actions
          setRiceData(riceData.filter((rice) => rice.id !== riceId));
        })
        .catch((error) => {
          console.error('Error deleting rice entry:', error);
        });
    }
  };
  

  return (
    <div className="container mt-5">
      <button
        className="btn btn-primary mb-3"
        onClick={() => navigate('/home')}
      >
        Back to Home
      </button>
      <div className="d-flex justify-content-between align-items-center">
        <h2>Store {store.storeId}</h2>
        <button
          className="btn btn-danger btn-sm"
          onClick={handleDelete}
        >
          Delete Store
        </button>
      </div>
      <div className="table-responsive">
        <table className="table table-sm table-bordered">
          <tbody>
            <tr>
              <th>Postcode</th>
              <td>{store.postcode}</td>
              <th>Population</th>
              <td>{store.population}</td>
            </tr>
            <tr>
              <th>Threshold Amount</th>
              <td>{store.thresamount}</td>
              <th>Minimum Amount</th>
              <td>{store.minamount}</td>
            </tr>
          </tbody>
        </table>
      </div>
      
      <div className="d-flex justify-content-between align-items-center mb-3">
        <h3>Rice Data</h3>
        <button
          className="btn btn-success"
          onClick={handleCreateRecord}
        >
          Create Month
        </button>
      </div>      
      <div className="row">
        {riceData.map((rice) => (
          <div key={rice.id} className="col-md-4 mb-4">
            <div className="card">
              <div className="card-body">
                <h5 className="card-title">Month: {formatDate(rice.month)}</h5>
                <p className="card-text">Prediction: {rice.prediction}
                {rice.prediction === null && (
                  <button
                    className="btn btn-primary"
                    onClick={() => handleCreatePrediction(rice.id)}
                  >
                    Create Prediction
                  </button>
                )}
                </p>
                <p className="card-text">Quantity: {rice.quantity}</p>
                <button
                  className="btn btn-danger"
                  onClick={() => handleDeleteRice(rice.id)}
                >
                  Delete Month
                </button>
              </div>
            </div>
          </div>
        ))}
      </div>
      {/* Incidents Table */}
      <div className="mt-4">
        <h3>Incidents</h3>
        {incidents.length > 0 ? (
          <table className="table table-bordered">
            <thead>
              <tr>
                <th>Date</th>
                <th>Description</th>
                {/* Add more columns as needed */}
              </tr>
            </thead>
            <tbody>
              {incidents.map((incident) => (
                <tr key={incident.id}>
                  <td>{incident.month}</td>
                  <td>{incident.description}</td>
                  {/* Add more cells for additional columns */}
                </tr>
              ))}
            </tbody>
          </table>
        ) : (
          <p>No incidents found for this store.</p>
        )}
      </div>
    </div>
  );
}

export default Store;
