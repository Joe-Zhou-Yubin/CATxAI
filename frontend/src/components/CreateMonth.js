import React, { useState } from 'react';
import axios from 'axios';
import { useParams, useNavigate } from 'react-router-dom';

function CreateMonth() {
  const { storeId } = useParams();
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    month: '',
    quantity: '',
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // Create a new rice record
    axios.post(`http://localhost:8080/api/rice/create/${storeId}`, formData)
      .then((response) => {
        // Redirect to the store page after successful creation
        navigate(`/store/${storeId}`);
      })
      .catch((error) => {
        console.error('Error creating rice entry:', error);
      });
  };

  return (
    <div className="container mt-5">
      <button
          className="btn btn-secondary ms-2"
          onClick={() => navigate(`/store/${storeId}`)}
        >
          Back to Store {storeId}
        </button>
      <h2>Create Month Record</h2>
      <form onSubmit={handleSubmit}>
        <div className="mb-3">
          <label htmlFor="month" className="form-label">
            Month (YYYY-MM):
          </label>
          <input
            type="date"
            className="form-control"
            id="month"
            name="month"
            value={formData.month}
            onChange={handleChange}
            required
          />
        </div>
        <div className="mb-3">
          <label htmlFor="quantity" className="form-label">
            Quantity:
          </label>
          <input
            type="number"
            className="form-control"
            id="quantity"
            name="quantity"
            value={formData.quantity}
            onChange={handleChange}
            required
          />
        </div>
        <button type="submit" className="btn btn-primary">
          Create Month Record
        </button>
        
      </form>
    </div>
  );
}

export default CreateMonth;
