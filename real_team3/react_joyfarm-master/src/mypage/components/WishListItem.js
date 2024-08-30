import React from 'react';
import { Link } from 'react-router-dom';
import styled from 'styled-components';
import { ImageBgBox } from '../../commons/components/ImageBox';
import { FaMapMarkerAlt } from 'react-icons/fa';
import { color } from '../../styles/color';
import { useParams } from 'react-router-dom';
import NoImage from '../../images/farm.jpg';
import { FcPhone } from 'react-icons/fc';
import fontSize from '../../styles/fontSize';

const { midGreen, darkGreen } = color;

const { medium } = fontSize;

const WishListItems = ({ item, className }) => {
  const { tab } = useParams();
  const Notel = '홈페이지 문의';
  const {
    seq,
    title,
    photoUrl1,
    address,
    photoUrl,
    townImage,
    townName,
    doroAddress,
    tel,
    ownerTel,
    subject,
    poster,
    createdAt,
  } = item;
  const displayImageUrl = photoUrl1 ?? photoUrl ?? townImage ?? NoImage;
  console.log(displayImageUrl);
  const displaytitle = title ?? townName;
  const displayaddress = address ?? doroAddress;
  const displaytel = tel ?? ownerTel ?? Notel;
  const url =
    tab === 'tour'
      ? `/recommend/${tab}/${seq}`
      : tab === 'festival'
      ? `/recommend/${tab}/${seq}`
      : tab === 'reservation'
      ? `/${tab}/info/${seq}`
      : tab === 'board'
      ? `/${tab}/view/${seq}`
      : '#';

  return (
    <li className={`${className} ${tab === 'board' ? 'board' : ''}`}>
      <Link to={url}>
        {tab !== 'board' && displayImageUrl && (
          <ImageBgBox
            className="photo"
            url={displayImageUrl}
            width="100%"
            height="165px"
          />
        )}
        <div className="item-content">
          {tab === 'board' ? (
            <div className="board-content">
              <div className="boardSubject">{subject}</div>
              <div className="boardPoster">{poster}</div>
              <div className="boardCreated">{createdAt}</div>
            </div>
          ) : (
            <>
              <div className="title">{displaytitle}</div>
              <div className="tel">
                <FcPhone className="tel_icon" />
                {displaytel}
              </div>
              <div className="address">
                <FaMapMarkerAlt className="icon" />
                {displayaddress}
              </div>
            </>
          )}
        </div>
      </Link>
    </li>
  );
};

const ItemStyledBox = styled(WishListItems)`
  padding: 15px;
  border: 2px solid ${midGreen};
  border-radius: 5px;
  width: 450px;
  height: 200px;
  box-sizing: border-box;
  margin-bottom: 20px;

  &:hover {
    font-weight: bold;
  }

  a {
    display: flex;

    .photo {
      width: 100%;
      border-radius: 5px;
    }

    .item-content {
      width: 100%;
      padding: 10px 0 10px 20px;
      display: flex;
      justify-content: space-between;
      flex-direction: column;
      overflow-wrap: break-word;

      .title {
        font-size: 18px;
        font-weight: bold;
        text-align: center;
        overflow-wrap: break-word;
        padding: 0 10px;
      }

      .tel {
        font-size: 15px;
        color: #767676;

        .tel_icon {
          position: relative;
          top: 3px;
          margin-right: 5px;
        }
      }

      .address {
        font-size: 15px;
        color: #767676;

        .icon {
          color: #ff5e00;
          position: relative;
          top: 3px;
          margin-right: 5px;
        }
      }
    }
  }
  &.board {
    display: flex;
    align-items: center;
    height: 80px;
    font-size: ${medium};
    padding: 10px;
    padding-right: 20px;
    &:hover {
      font-weight: bold;
    }

    .board-content {
      display: flex;
      justify-content: space-between;
      align-items: center;
      text-align: center;
      width: 100%;

      .boardCreated {
        width: 120px;
        height: 20px;
        overflow: hidden;
        text-overflow: ellipsis;
      }

      .boardSubject {
        width: 150px;
        height: 20px;
        overflow: hidden;
        text-overflow: ellipsis;
        font-weight: bold;
        background: #e2f7dd;
        border-radius: 20px;
      }

      .boardPoster {
        width: 120px;
        height: 20px;
        overflow: hidden;
        text-overflow: ellipsis;
      }
    }
  }
`;

const WishListItem = ({ items }) => {
  return (
    items.length > 0 &&
    items.map((item) => <ItemStyledBox key={item.seq} item={item} />)
  );
};

export default React.memo(WishListItem);
