import axios from 'axios';

const API_URL = 'http://localhost:8080/starlight/customer';

export const createCustomer = (customer) => {
    return axios.post(`${API_URL}/create`, customer);
};

export const getCustomer = (customerId) => {
    return axios.get(`${API_URL}/read/${customerId}`);
};

export const updateCustomer = (customer) => {
    return axios.post(`${API_URL}/update`, customer);
};

export const deleteCustomer = (customerId) => {
    return axios.delete(`${API_URL}/delete/${customerId}`);
};

export const login = (email, password) => {
    return axios.get(`${API_URL}/login/${email}/${password}`);
};

export const getAllCustomers = () => {
    return axios.get(`${API_URL}/getall`);
};

export const userApi = {
  createCustomer,
  getCustomer,
  login,
  updateCustomer,
  deleteCustomer,
  getAllCustomers,
};