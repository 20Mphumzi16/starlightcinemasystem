import axios from 'axios';

const API_URL = 'http://localhost:8080/starlight/movie';

export const createMovie = (movie) => {
    return axios.post(`${API_URL}/create`, movie);
};

export const getMovie = (movieId) => {
    return axios.get(`${API_URL}/read/${movieId}`);
};

export const updateMovie = (movie) => {
    return axios.post(`${API_URL}/update`, movie);
};

export const deleteMovie = (movieId) => {
    return axios.delete(`${API_URL}/delete/${movieId}`);
};

export const getAllMovies = () => {
    return axios.get(`${API_URL}/getall`);
};

export const movieApi = {
  createMovie,
  getMovie,
  updateMovie,
  deleteMovie,
  getAllMovies,
};