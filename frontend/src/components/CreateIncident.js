import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function CreateIncident() {
  const [formData, setFormData] = useState({
    month: '',
    description: '',
    affstore: [],
  });

  const [stores, setStores] = useState([]);
  const navigate = useNavigate(); // Initialize the navigate function

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

  const handleStoreChange = (e) => {
    const { value } = e.target;
    const updatedAffStores = [...formData.affstore];

    if (updatedAffStores.includes(value)) {
      // If the store ID is already in the array, remove it
      updatedAffStores.splice(updatedAffStores.indexOf(value), 1);
    } else {
      // If the store ID is not in the array, add it
      updatedAffStores.push(value);
    }

    setFormData({ ...formData, affstore: updatedAffStores });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await axios.post('http://localhost:8080/api/inci/create', formData);

      console.log('Incident created:', response.data);
      navigate('/incidents'); 
    } catch (error) {
      console.error('Error creating incident:', error);
    }
  };

  return (
    <div className="container mt-5">
      <button
          type="button"
          className="btn btn-secondary ms-2"
          onClick={() => navigate('/incidents')} // Go back to the Incidents page
        >
          Back to Incidents
        </button>
      <h2>Create Incident</h2>
      <form onSubmit={handleSubmit}>
        <div className="mb-3">
          <label htmlFor="month" className="form-label">
            Month
          </label>
          <input
            type="date"
            className="form-control"
            id="month"
            name="month"
            value={formData.month}
            onChange={(e) => setFormData({ ...formData, month: e.target.value })}
            required
          />
        </div>
        <div className="mb-3">
          <label htmlFor="description" className="form-label">
            Description
          </label>
          <textarea
            className="form-control"
            id="description"
            name="description"
            value={formData.description}
            onChange={(e) => setFormData({ ...formData, description: e.target.value })}
            required
          />
        </div>
        <div className="mb-3">
          <label className="form-label">Affected Stores</label>
          {stores.map((store) => (
            <div className="form-check" key={store.storeId}>
              <input
                className="form-check-input"
                type="checkbox"
                value={store.storeId}
                id={store.storeId}
                checked={formData.affstore.includes(store.storeId)}
                onChange={handleStoreChange}
              />
              <label className="form-check-label" htmlFor={store.storeId}>
                {store.storeId}
              </label>
            </div>
          ))}
        </div>
        <button type="submit" className="btn btn-primary">
          Create Incident
        </button>
      </form>
    </div>
  );
}

export default CreateIncident;
