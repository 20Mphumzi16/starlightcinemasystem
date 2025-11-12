import axios from 'axios';

const API_URL = 'http://localhost:8080/starlight/show';

export const createShow = (show) => {
    return axios.post(`${API_URL}/create`, show);
};

export const getShow = (showId) => {
    return axios.get(`${API_URL}/read/${showId}`);
};

export const updateShow = (show) => {
    return axios.post(`${API_URL}/update`, show);
};

export const deleteShow = (showId) => {
    return axios.delete(`${API_URL}/delete/${showId}`);
};

export const getAllShows = () => {
    return axios.get(`${API_URL}/getall`);
};

export const showApi = {
  createShow,
  getShow,
  updateShow,
  deleteShow,
  getAllShows,
};