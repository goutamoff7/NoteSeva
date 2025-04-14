import React from 'react'
import Footer from '../components/Footer'
import HSearchBar from '../components/HomeParts/HSearchBar'
import Hchatbot from '../components/HomeParts/Hchatbot'
import HCourses from '../components/HomeParts/HCourses'
import HFeatures from '../components/HomeParts/HFeatures'
import HContribute from '../components/HomeParts/HContribute'
import HCommunities from '../components/HomeParts/HCommunities'
import HTestimonials from '../components/HomeParts/HTestimonials'


const Home = () => {
  return (
    <div className="bg-[url('darkbg.png')] bg-darkbg bg-cover bg-center w-full h-full flex flex-col shadow-md relative">

        <HCommunities/>

        <HSearchBar/>

        <Hchatbot/>

        <HCourses/>

        <HFeatures/>

        <HContribute/>

        <HTestimonials/>
      
        <Footer></Footer>

    </div>
  )
}

export default Home