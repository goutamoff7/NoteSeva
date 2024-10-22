import React from 'react'
import Footer from '../components/Footer'
import HSearchBar from '../components/HomeParts/HSearchBar'
import Hchatbot from '../components/HomeParts/Hchatbot'
import HCourses from '../components/HomeParts/HCourses'
import HFeatures from '../components/HomeParts/HFeatures'
import HContribute from '../components/HomeParts/HContribute'



const Home = () => {
  return (
    <div className="bg-[url('darkbg.png')] bg-cover bg-center w-full h-full bg-repeat flex flex-col shadow-md">

        <HSearchBar/>

        <Hchatbot/>

        <HCourses/>

        <HFeatures/>

        <HContribute/>

          
        
        <Footer></Footer>

    </div>
  )
}

export default Home