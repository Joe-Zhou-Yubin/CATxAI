import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function CreateStore() {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    postcode: '',
    population: '',
    thresamount: '',
    minamount: '',
  });

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await axios.post('http://localhost:8080/api/stores/create', formData);

      console.log('Store created:', response.data);
      navigate('/home'); // Redirect to the User Dashboard or the desired route
    } catch (error) {
      console.error('Error creating store:', error);
    }
  };

  return (
    <div className="container mt-5">
      <button className="btn btn-primary mb-3" onClick={() => navigate('/home')}>
        Back to Home
      </button>
      <h2>Create Store</h2>
      <form onSubmit={handleSubmit}>
        <div className="mb-3">
          <label htmlFor="postcode" className="form-label">
            Postcode
          </label>
          <input
            type="text"
            className="form-control"
            id="postcode"
            name="postcode"
            value={formData.postcode}
            onChange={(e) => setFormData({ ...formData, postcode: e.target.value })}
            required
          />
        </div>
        <div className="mb-3">
          <label htmlFor="population" className="form-label">
            Population
          </label>
          <input
            type="number"
            className="form-control"
            id="population"
            name="population"
            value={formData.population}
            onChange={(e) => setFormData({ ...formData, population: e.target.value })}
            required
          />
        </div>
        <div className="mb-3">
          <label htmlFor="thresamount" className="form-label">
            Threshold Amount
          </label>
          <input
            type="number"
            className="form-control"
            id="thresamount"
            name="thresamount"
            value={formData.thresamount}
            onChange={(e) => setFormData({ ...formData, thresamount: e.target.value })}
            required
          />
        </div>
        <div className="mb-3">
          <label htmlFor="minamount" className="form-label">
            Minimum Amount
          </label>
          <input
            type="number"
            className="form-control"
            id="minamount"
            name="minamount"
            value={formData.minamount}
            onChange={(e) => setFormData({ ...formData, minamount: e.target.value })}
            required
          />
        </div>
        <button type="submit" className="btn btn-primary">
          Create Store
        </button>
      </form>
    </div>
  );
}

export default CreateStore;
