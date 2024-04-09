import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import { App, About, Contact } from "./App";
import {
    BrowserRouter,
    Routes,
    Route
} from "react-router-dom";

// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
import LoginPage from "./pages/LoginPage";
import CreateAccountPage from "./pages/CreateAccountPage";
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
const firebaseConfig = {
    apiKey: "YOUR INFO HERE"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <BrowserRouter>
        <Routes>
            <Route path="/" element={<App />} />
            <Route path="/about" element={<About />} />
            <Route
                path="/contact"
                element={<Contact />}
            />
            <Route path="/login" element={<LoginPage />} />
            <Route path="/create-account" element={<CreateAccountPage />} />
        </Routes>
    </BrowserRouter>,
);