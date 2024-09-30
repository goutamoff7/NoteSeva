// src/App.jsx
import { Routes, Route } from 'react-router-dom';
import Home from './pages/Home';
import About from './pages/About';
import Course from './pages/Course';
import Project from './pages/Project';
import Contribute from './pages/Contribute';
import Contact from './pages/Contact';
import Navbar from './components/Navbar';


function App() {
  return (
    <div>
      <Navbar/>

      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/course" element={<Course />} />
        <Route path="/project" element={<Project />} />
        <Route path="/about" element={<About />} />
        <Route path="/contact" element={<Contact />} />
        <Route path="/contribute" element={<Contribute />} />
      </Routes>
    </div>
  );
}

export default App;
