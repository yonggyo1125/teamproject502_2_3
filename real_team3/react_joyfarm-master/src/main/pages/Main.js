import React, { useRef } from 'react';
import { useNavigate } from 'react-router-dom';
import MainBanner from './components/MainBanner';
import MainLocation from './components/MainLocation';
import MainRecommend from './components/MainRecommend';
import MainReview from './components/MainReview';
import MainBoard from './components/MainBoard';
import Sidebar from './components/SideBar';

const HEADER_HEIGHT = 300;

const Main = () => {
  const navigate = useNavigate();

  const top = useRef(null);
  const locationRef = useRef(null);
  const recommendRef = useRef(null);
  const reviewRef = useRef(null);
  const boardRef = useRef(null);

  const handleButtonReservation = () => {
    navigate('/reservation/list');
  };

  const handleButtonLocation = () => {
    navigate('/recommend/festival');
  };

  const handleButtonReview = () => {
    navigate('/board/list/review');
  };

  const handleButtonRecommend = () => {
    navigate('/recommend/tour');
  };

  const handleButtonBoard = () => {
    navigate('/board/list/notice');
  };

  const scrollToSection = (ref, offset = 0) => {
    if (ref.current) {
      const topOffset =
        ref.current.getBoundingClientRect().top + window.pageYOffset - offset;
      window.scrollTo({ top: topOffset, behavior: 'smooth' });
    }
  };

  const handleLinkClick = (section) => {
    switch (section) {
      case 'top':
        scrollToSection(top, HEADER_HEIGHT);
        break;
      case 'location':
        scrollToSection(locationRef);
        break;
      case 'recommend':
        scrollToSection(recommendRef);
        break;
      case 'review':
        scrollToSection(reviewRef);
        break;
      case 'board':
        scrollToSection(boardRef);
        break;
      default:
        break;
    }
  };

  return (
    <>
      <div ref={top}>
        <MainBanner onButtonClick={handleButtonReservation} />
      </div>
      <div ref={locationRef}>
        <MainLocation onButtonClick={handleButtonLocation} />
      </div>
      <div ref={recommendRef}>
        <MainRecommend onButtonClick={handleButtonRecommend} />
      </div>
      <div ref={reviewRef}>
        <MainReview onButtonClick={handleButtonReview} />
      </div>
      <div ref={boardRef}>
        <MainBoard onButtonClick={handleButtonBoard} />
      </div>
      <Sidebar onLinkClick={handleLinkClick} />
    </>
  );
};

export default React.memo(Main);
