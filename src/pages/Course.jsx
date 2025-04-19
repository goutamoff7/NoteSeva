import Card from "../components/Card";
import Footer from "../components/Footer";
import { courses } from "../../data/data";

const Course = () => {
  return (
    <div className="bg-darkbg max-w-full flex flex-col">
      {/* Course cards in a 4-column grid */}
      <div className="my-[80px] max-w-[80%] pl-5 flex flex-wrap justify-center gap-12 mx-auto">
        {courses.map((courses, index) => (
          <Card
            key={index} 
            title={courses.Title}
            image={courses.shortImage}
            para={courses.description}
          />
        ))}
      </div>



      <Footer />
    </div>
  );
};

export default Course;
