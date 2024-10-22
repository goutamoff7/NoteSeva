// src/App.jsx
import { Routes, Route } from 'react-router-dom';
import Home from './pages/Home';
import Course from './pages/Course';
import Project from './pages/Project';
import Contribute from './pages/Contribute';
import Chatbot from './pages/Chatbot';
import Navbar from './components/Navbar';
import Login from './pages/Login';
import SignUp from './pages/SignUp';
import NotesUpload from './pages/NotesUpload';
import ProjectUpload from './pages/ProjectUpload';


function App() {
  return (
    <div>
      <Navbar/>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/course" element={<Course />} />
        <Route path="/project" element={<Project />} />
        <Route path="/Chatbot" element={<Chatbot />} />
        <Route path="/contribute" element={<Contribute />} />
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<SignUp />} />
        <Route path="/contribute/notesUpload" element={<NotesUpload />} />
        <Route path="/contribute/projectUpload" element={<ProjectUpload />} />
      </Routes>
    </div>
  );
}

export default App;
