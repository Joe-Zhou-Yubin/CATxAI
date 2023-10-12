import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link, useNavigate } from 'react-router-dom';

function Incident() {
  const [incidents, setIncidents] = useState([]);
  const navigate = useNavigate(); // Initialize the navigate function

  useEffect(() => {
    // Fetch the list of incidents from the API
    axios.get('http://localhost:8080/api/inci/list')
      .then((response) => {
        setIncidents(response.data);
      })
      .catch((error) => {
        console.error('Error fetching incidents:', error);
      });
  }, []); // Empty dependency array to run the effect only once

  const handleDelete = (id) => {
    // Confirm the delete action with the user (you can use a confirmation dialog)
    if (window.confirm('Are you sure you want to delete this incident?')) {
      // Send a delete request to the server
      axios.delete(`http://localhost:8080/api/inci/delete/${id}`)
        .then(() => {
          // Remove the deleted incident from the local state
          setIncidents((prevIncidents) => prevIncidents.filter((incident) => incident.id !== id));
          alert('Incident deleted successfully');
        })
        .catch((error) => {
          console.error('Error deleting incident:', error);
        });
    }
  };

  return (
    <div className="container mt-5">
      <h2>Incidents</h2>
      <button
          className="btn btn-primary btn-sm"
          onClick={() => navigate('/createincident')} // Use navigate to go to the createincident route
        >
          Create Incident
        </button>
      <table className="table table-bordered">
        <thead>
          <tr>
            <th>ID</th>
            <th>Month</th>
            <th>Description</th>
            <th>Affected Stores</th>
            <th>Delete</th> {/* Add a Delete column */}
          </tr>
        </thead>
        <tbody>
          {incidents.map((incident) => (
            <tr key={incident.id}>
              <td>{incident.id}</td>
              <td>{incident.month}</td>
              <td>{incident.description}</td>
              <td>
                <ul>
                  {incident.affstore.map((storeId) => (
                    <li key={storeId}>{storeId}</li>
                  ))}
                </ul>
              </td>
              <td>
                <button
                  className="btn btn-danger btn-sm"
                  onClick={() => handleDelete(incident.id)}
                >
                  Delete
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default Incident;
