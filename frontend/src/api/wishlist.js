import axios from 'axios';

const API_URL = 'http://localhost:8080/starlight/wishlist'; // Adjust the base URL as needed

export const createWishlist = (wishlist) => {
    return axios.post(`${API_URL}/create`, wishlist);
};

export const getWishlist = (wishlistId) => {
    return axios.get(`${API_URL}/read/${wishlistId}`);
};

export const updateWishlist = (wishlist) => {
    return axios.post(`${API_URL}/update`, wishlist);
};

export const deleteWishlist = (wishlistId) => {
    return axios.delete(`${API_URL}/delete/${wishlistId}`);
};

export const getall = () => {
    return axios.get(`${API_URL}/getall`);
};
export const removeShowFromWishlist = (wishlistId, showId) => {
    return axios.post(`${API_URL}/${wishlistId}/removeShow/${showId}`);
};
export const addShowToWishlist = (wishlistId, showId) => {
    return axios.post(`${API_URL}/${wishlistId}/addShow/${showId}`);
};
export const getCustomerWishlist = (email) => {
    return axios.get(`${API_URL}/getCustomerWishlist/${email}`);
};
export const assignWishlistToCustomer = (customerId) => {
    return axios.post(`${API_URL}/assignWishlist/${customerId}`);
};

export const wishlistApi = {
    createWishlist,
    getWishlist,
    updateWishlist,
    deleteWishlist,
    getall,
    removeShowFromWishlist,
    addShowToWishlist,
    getCustomerWishlist,
    assignWishlistToCustomer
};


