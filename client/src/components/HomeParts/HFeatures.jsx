// src/components/Features.js
import React from 'react';
import FeatureCard from './FeatureCard';

const features = [
  {
    title: 'Previous Year Questions',
    description: 'Simple and secure control of your organization’s financial and legal transactions. Send customized invoices and contracts.',
    icon: 'pyq.png',
  },
  {
    title: 'College Notes by Professors & Students',
    description: 'Schedule and reserve classrooms at one campus or multiple campuses. Keep detailed records of student attendance.',
    icon: 'notes.png',
  },
  {
    title: 'University Organisers',
    description: 'Automate and track emails to individuals or groups. Skilline’s built-in system helps organize your organization.',
    icon: 'organizer.png',
  },
];

const Features = () => {
  return (
    <div className="my-[80px]">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 text-center">
        <h2 className="text-3xl font-bold text-white mb-4">
          All-In-One <span className="text-green-400">Cloud Software</span>
        </h2>
        <p className="text-gray-300 mb-10">
          Skilline is one powerful online software suite that combines all the tools needed to run a successful school or office.
        </p>

        <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-8">
          {features.map((feature, index) => (
            <FeatureCard
              key={index}
              title={feature.title}
              description={feature.description}
              icon={feature.icon}
            />
          ))}
        </div>
      </div>
    </div>
  );
};

export default Features;
