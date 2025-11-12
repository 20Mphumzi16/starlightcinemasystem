import axios from 'axios';

const API_URL = 'http://localhost:8080/starlight/seat';

export const createSeat = (seat) => {
    return axios.post(`${API_URL}/create`, seat);
};

export const getSeat = (seatId) => {
    return axios.get(`${API_URL}/read/${seatId}`);
};

export const updateSeat = (seat) => {
    return axios.post(`${API_URL}/update`, seat);
};

export const deleteSeat = (seatId) => {
    return axios.delete(`${API_URL}/delete/${seatId}`);
};

export const getAllSeats = () => {
    return axios.get(`${API_URL}/getall`);
};

export const seatApi = {
  createSeat,
  getSeat,
  updateSeat,
  deleteSeat,
  getAllSeats,
};