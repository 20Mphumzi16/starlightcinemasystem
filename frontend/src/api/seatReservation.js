import axios from 'axios';

const API_URL = 'http://localhost:8080/starlight/seatReservation';

export const createSeatReservation = (seatReservation) => {
    return axios.post(`${API_URL}/create`, seatReservation);
};

export const getSeatReservation = (seatReservationId) => {
    return axios.get(`${API_URL}/read/${seatReservationId}`);
};

export const updateSeatReservation = (seatReservation) => {
    return axios.post(`${API_URL}/update`, seatReservation);
};

export const deleteSeatReservation = (seatReservationId) => {
    return axios.delete(`${API_URL}/delete/${seatReservationId}`);
};

export const getAllSeatReservations = () => {
    return axios.get(`${API_URL}/getall`);
};

export const seatReservationApi = {
  createSeatReservation,
  getSeatReservation,
  updateSeatReservation,
  deleteSeatReservation,
  getAllSeatReservations,
};
