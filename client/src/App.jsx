// src/App.jsx
import { Routes, Route } from 'react-router-dom';
import Home from './pages/Home';
import Course from './pages/Course';
import Project from './pages/Project';
import Contribute from './pages/Contribute';
import Contact from './pages/Contact';
import Navbar from './components/Navbar';
import Login from './pages/Login';
import SignUp from './pages/SignUp';


function App() {
  return (
    <div>
      <Navbar/>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/course" element={<Course />} />
        <Route path="/project" element={<Project />} />
        <Route path="/contact" element={<Contact />} />
        <Route path="/contribute" element={<Contribute />} />
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<SignUp />} />
      </Routes>
    </div>
  );
}

export default App;
