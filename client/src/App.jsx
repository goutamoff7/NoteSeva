import { Routes, Route } from "react-router-dom";
import ProtectedRoute from "./utils/ProtectedRoute";
import PublicRoute from "./utils/PublicRoute";

import Home from "./pages/Home";
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
import Bookmarked from "./pages/Bookmarked";
import Features from "./pages/Features";
import Dashboard from "./pages/Dashboard";
import Resources from "./pages/Resources";
import ForgotPassword from "./pages/ForgotPassword";
import ChangePassword from "./pages/ChangePassword";
import organiserUpload from "./pages/organizerUpload";
import PYQUpload from "./pages/PYQUpload";

function App() {
  return (
    <div>
      <Navbar />
      <Routes>
        {/* Publicly accessible */}
        <Route path="/" element={<Home />} />
        <Route path="/contactUs" element={<Contact />} />
        <Route path="/features" element={<Features />} />
        <Route path="/contribute" element={<Contribute />} />
        <Route path="/project" element={<Project />} />

        {/* Public-only routes (block if logged in) */}
        <Route element={<PublicRoute />}>
          <Route path="/login" element={<Login />} />
          <Route path="/signup" element={<SignUp />} />
          <Route path="/forgot-password" element={<ForgotPassword />} />
        </Route>

        {/* Protected routes (require login) */}
        <Route element={<ProtectedRoute />}>
          <Route path="/contribute/notesUpload" element={<NotesUpload />} />
          <Route path="/contribute/organizerUpload" element={<organizerUpload />} />
          <Route path="/contribute/pyqUpload" element={<PYQUpload />} />
          <Route path="/contribute/projectUpload" element={<ProjectUpload />} />
          <Route path="/features/:feature" element={<Resources />} />
          <Route path="/project/:projectId" element={<ProjectView />} />
          <Route path="/my-profile" element={<MyProfile />} />
          <Route path="/bookmarked" element={<Bookmarked />} />
          <Route path="/dashboard" element={<Dashboard />} />
          <Route path="/change-password" element={<ChangePassword />} />
        </Route>
      </Routes>
    </div>
  );
}

export default App;
