import React from 'react';
import CourseCard from './CourseCard';

const courses = [
  { title: 'PHARMACY', image: 'bpharm.png' },
  { title: 'ENGINEERING', image: 'btech.png' },
  { title: 'MBBS', image: 'mbbs.png' },
  { title: 'Nursing', image: 'bnurs.png' },
  { title: 'ARCHITECTURE', image: 'barch.png' },
  { title: 'DIPLOMA', image: 'diploma.png' },
];

const Courses = () => {
  return (
    <div className="my-[40px]">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <h2 className="text-3xl font-bold text-whitee text-center mb-10">Our Courses</h2>
        <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-8">
          {courses.map((course, index) => (
            <CourseCard key={index} title={course.title} image={course.image} />
          ))}
        </div>
      </div>
    </div>
  );
};

export default Courses;
