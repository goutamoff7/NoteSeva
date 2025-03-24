// src/App.jsx
import { Routes, Route } from "react-router-dom";
import Home from "./pages/Home";
import Course from "./pages/Course";
import Project from "./pages/Project";
import Contribute from "./pages/Contribute";
import Navbar from "./components/Navbar";
import Login from "./pages/Login";
import SignUp from "./pages/SignUp";
import NotesUpload from "./pages/NotesUpload";
import ProjectUpload from "./pages/ProjectUpload";
import ProjectView from "./pages/ProjectView";
import Contact from "./pages/ContactUs";
import MyProfile from "./pages/MyProfile";
import Bookmarked from "./pages/Bookmarked ";
import Features from "./pages/Features";
import Dashboard from "./pages/Dashboard";
import Resources from "./pages/Resources";
import ForgotPassword from "./pages/ForgotPassword";
import ResetPassword from "./pages/ResetPassword";

function App() {
  return (
    <div>
      <Navbar />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/project" element={<Project />} />
        <Route path="/project/:projectId" element={<ProjectView />} />
        <Route path="/contribute" element={<Contribute />} />
        <Route path="/contribute/notesUpload" element={<NotesUpload />} />
        <Route path="/contribute/projectUpload" element={<ProjectUpload />} />
        <Route path="/contactUs" element={<Contact />} />
        <Route path="/features" element={<Features />} />
        <Route path="/features/:feature" element={<Resources />} />

        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<SignUp />} />
        <Route path="/forgot-password" element={<ForgotPassword />} />


        <Route path="/my-profile" element={<MyProfile />} />
        <Route path="/bookmarked" element={<Bookmarked />} />
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/reset-password" element={<ResetPassword />} />


      </Routes>
    </div>
  );
}

export default App;
