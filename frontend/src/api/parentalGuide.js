import axios from 'axios';

const API_URL = 'http://localhost:8080/starlight/pg';

export const createParentalGuide = (guide) => {
    return axios.post(`${API_URL}/create`, guide);
};

export const getParentalGuide = (guideId) => {
    return axios.get(`${API_URL}/read/${guideId}`);
};

export const updateParentalGuide = (guide) => {
    return axios.post(`${API_URL}/update`, guide);
};

export const deleteParentalGuide = (guideId) => {
    return axios.delete(`${API_URL}/delete/${guideId}`);
};

export const getAllParentalGuides = () => {
    return axios.get(`${API_URL}/get-all`);
};

export const parentalGuideApi = {
  createParentalGuide,
  getParentalGuide,
  updateParentalGuide,
  deleteParentalGuide,
  getAllParentalGuides,
};

 