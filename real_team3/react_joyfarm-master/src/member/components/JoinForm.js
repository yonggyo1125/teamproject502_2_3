import React from 'react';
import { useTranslation } from 'react-i18next';
import styled from 'styled-components';
import { FaCheckSquare, FaRegCheckSquare } from 'react-icons/fa';
import { BigButton, ButtonGroup } from '../../commons/components/Buttons';
import InputBox from '../../commons/components/InputBox';
import MessageBox from '../../commons/components/MessageBox';
import FileUpload from '../../commons/components/FileUpload';
import ProfileImage from './ProfileImage';
import { color } from '../../styles/color';
import fontSize from '../../styles/fontSize';

const { whiteGray, white, whiteGreen, lightGreen, midGreen, danger } = color;
const { medium } = fontSize;

const FormBox = styled.form`
//background-color: ${whiteGreen}; /* 부드러운 배경색 추가 */
padding: 30px;
border-radius: 30px;
margin: 30px;
border: 1px solid ${whiteGray};
box-shadow: 0 4px 4px rgba(0, 0, 0, 0.1); /* 가벼운 그림자 추가 */
max-width: 1000px; /* 최대 너비 설정 */
transition: all 0.3s ease;
font-size: ${medium};
.oMZKK {
color: ${danger};
}

 textarea {
    display: block;
    width: 100%;
    height: 150px; /* 원하는 높이 설정 */
    overflow-y: 10px; /* 수직 스크롤 가능 */
    margin-top: 15px;
    margin-bottom: 25px; /* 하단 여백 추가 */
    border: 3px solid #ccc; /* 테두리 추가 */
    border-radius: 5px; /* 모서리 둥글게 */
    padding: 13px; /* 내부 여백 추가 */
    background-color: #f9f9f9; /* 배경 색상 추가 */
  }

  dl {
  
    display: flex;
    align-items: center;
    margin: 5px 0;

    dt {
    
      width: 120px;
      font-weight: bold;
    }
    
    dd {
      flex-grow: 1;
      
      input {
        font-size: ${medium};
        width: 100%;
        height: 40px;
        border: 1px solid #ccc;
        border-radius: 7px;
        margin-top: 12px;
        margin-left: 3px;
        transition: border-color 0.3s ease, box-shadow 0.3s ease;
      }
    }
  }

  dl + dl {
    border-bottom: 1px solid ${whiteGray};
  }

  .password{
    border-bottom: 1px solid ${whiteGray};
  }

  .profile{
    padding: 10px 0 15px 0;
    
  }

  .auCode {
  width: 300px;
  }

  .terms-agree {
    text-align: center;
    margin-bottom: 20px;
    cursor: pointer;
    transition: color 0.3s ease;

    svg {
      font-size: 1.5rem;
      margin-right: 2px;
      vertical-align: middle;
      color: #007bff; /* 아이콘 색상 변경 */
    }
  }
    
  }
`;

const EmailVerificationBox = styled.div`
  .rows {
    display: flex;
    flex-direction: row;
    margin-bottom: -25px;

    input {
      margin-top: 0;
    }

    button {
      width: 150px;
      height: 40px;
      border-radius: 5px;
      margin-left: 10px;
      padding: 0 10px;
      border: 1px solid ${whiteGray};
      background: ${whiteGreen};
      color: black;
      cursor: pointer;
      font-size: ${medium};
    }

    .authCount {
      margin-left: 10px;
      font-size: ${medium};
    }
  }
`;

const StyledButton = styled.button`
  width: 100px;
  height: 40px;
  border-radius: 5px;
  margin-left: 5px;
  font-size: 1rem;
  border: 1px solid ${whiteGray};
  background: ${whiteGreen};
  color: black;
  cursor: pointer;
  font-size: ${medium};
`;

const StyledButtons = styled.div`
  display: flex;
  justify-content: center;

  button {
    font-size: 1.25rem;
    border-radius: 5px;
    width: 150px;
    height: 40px;
    border: none;
    background: ${midGreen};
    color: ${white};
    font-size: ${medium};
    margin: 0 3px 0 3px;
    cursor: pointer;
  }
`;

const JoinForm = ({
  form,
  onSubmit,
  onChange,
  onToggle,
  onReset,
  onSendAuthCode,
  onReSendAuthCode,
  onVerifyAuthCode,
  errors,
  fileUploadCallback,
  fileDeleteCallback,
}) => {
  const { t } = useTranslation();
  return (
    <FormBox autoComplete="off" onSubmit={onSubmit}>
      <div className="form_box">
        <EmailVerificationBox>
          <dl>
            <dt>{t('이메일')}</dt>
            <dd>
              <div className="rows">
                <InputBox
                  type="text"
                  name="email"
                  placeholder={t('이메일을_입력하세요')}
                  value={form.email ?? ''}
                  onChange={onChange}
                  readOnly={
                    form.emailVerified ||
                    (form.authCount > 0 && form.authCount < 180)
                  }
                />
                {!form.emailVerified && form.authCount > 0 && (
                  <button
                    type="button"
                    onClick={onSendAuthCode}
                    disabled={form.authCount < 180 && form.authCount > 0}
                  >
                    {t('인증코드_전송')}
                  </button>
                )}
              </div>
            </dd>
          </dl>

          {form.emailVerified ? (
            <MessageBox>{t('확인된_이메일_입니다')}</MessageBox>
          ) : (
            <dl>
              <dt>{t('인증코드')}</dt>
              <dd>
                {form.authCount > 0 && (
                  <InputBox
                    type="text"
                    name="authNum"
                    className="auCode"
                    placeholder={t('인증코드_입력')}
                    onChange={onChange}
                  />
                )}
              </dd>
              <span className="authCount">{form.authCountMin}</span>
              <StyledButton type="button" onClick={onReSendAuthCode}>
                {t('재전송')}
              </StyledButton>
              <StyledButton
                type="button"
                className="btn2"
                onClick={onVerifyAuthCode}
              >
                {t('확인')}
              </StyledButton>
            </dl>
          )}

          <MessageBox messages={errors.email} />
        </EmailVerificationBox>
        <dl className="password">
          <dt>{t('비밀번호')}</dt>
          <dd>
            <InputBox
              type="password"
              name="password"
              placeholder={t('비밀번호를_입력하세요')}
              value={form.password ?? ''}
              onChange={onChange}
            />
            <MessageBox color="danger" messages={errors.password} />
          </dd>
        </dl>
        <dl>
          <dt>{t('비밀번호_확인')}</dt>
          <dd>
            <InputBox
              type="password"
              name="confirmPassword"
              placeholder={t('비밀번호를_확인하세요')}
              value={form.confirmPassword ?? ''}
              onChange={onChange}
            />
            <MessageBox color="danger" messages={errors.confirmPassword} />
          </dd>
        </dl>
        <dl>
          <dt>{t('회원명')}</dt>
          <dd>
            <InputBox
              type="text"
              name="userName"
              placeholder={t('회원명을_입력하세요')}
              value={form.userName ?? ''}
              onChange={onChange}
            />
            <MessageBox color="danger" messages={errors.userName} />
          </dd>
        </dl>
        <dl>
          <dt>{t('휴대전화번호')}</dt>
          <dd>
            <InputBox
              type="text"
              name="mobile"
              placeholder={t('전화번호를_입력하세요')}
              value={form.mobile ?? ''}
              onChange={onChange}
            />

            <MessageBox color="danger" messages={errors.mobile} />
          </dd>
        </dl>
        <dl className="profile">
          <dt>{t('프로필_이미지')}</dt>
          <dd>
            {form.profile && (
              <ProfileImage
                items={form.profile}
                width="250px"
                height="250px"
                radius="5px"
                onDelete={fileDeleteCallback}
              />
            )}
            <FileUpload
              width={150}
              color="primary"
              gid={form.gid}
              imageOnly={true}
              callback={fileUploadCallback}
            >
              {t('업로드')}
            </FileUpload>
          </dd>
        </dl>
        {/* 변경하기 기능 안되므로, 프로필 이미지의 X 클릭하고 다시 업로드버튼 클릭!
        <dd className='uploadImg'>
          <ImageUpload gid="testgid">{t('변경하기')}</ImageUpload>
        </dd>*/}

        <textarea readOnly="">
          01.고객의 개인정보 보호 ㈜🍀JOY_FARM (이하 "회사"라고 함)는 고객의
          개인정보를 중요시하며, '개인정보보호법', '정보통신망 이용촉진 및
          정보보호 등에 관한 법률' 등 개인정보와 관련된 법령을 준수하고
          있습니다. 02. 개인정보의 수집·이용목적, 수집하는 개인정보의 항목 및
          수집방법 가. 개인정보의 수집·이용목적과 수집하는 개인정보의 항목 ①
          회사는 회원서비스 (농촌활동 및 농촌체험 소개, 여행 상품 소개, 이벤트
          응모 등을 비롯한 어플리케이션 서비스 등 현재 제공 중이거나 향후 제공
          될 서비스) 등을 통하여 고객들에게 보다 더 향상된 양질의 서비스를
          제공하기 위하여 고객 개인의 정보를 수집·이용하고 있습니다.
          수집·이용정보항목과 수집·이용 목적은 아래와 같습니다. [수집·이용정보
          항목] - 성명, 성별, 생년월일, ID, 비밀번호, 주소, 이메일, 쿠키 - 만
          14세 미만인 경우 추가 수집·이용정보 항목 : 법정대리인(부모 등)의 성명,
          생년월일, 휴대전화인증 정보, 쿠키 [수집·이용목적] (1) 회원관리 서비스
          이용에 따른 본인식별, 법정대리인 동의 유무 확인, 법정대리인 본인의
          확인, 연령별 서비스의 제공, 불량회원의 부정이용 방지와 비인가
          사용방지, 분쟁 해결, 불만처리 등 민원처리, 문의사항처리 (2) 이벤트
          운영 및 고객 연락 추첨행사, 이벤트 운영, 이벤트 결과 공지, 경품 증정,
          사은품 증정, 경품·사은품·기타 물품의 배송, 청구서 전달, 본인의사 확인,
          컨텐츠 제공 및 이와 관련한 고객 연락, 각종 세금 신고·납부 및 공제,
          회사에 부과되는 법적·행정적 의무의 이행 등 (3) 마케팅 및 광고에 활용
          고객 만족도 조사, 여론조사 등의 통계학적 분석, 이벤트나 상품 등의
          광고성 정보 전달 고객의 기본적 인권 침해의 우려가 있는 민감한
          개인정보(인종 및 민족, 사상 및 신조, 출신지 및 본적지, 정치적 성향 및
          범죄기록, 건강상태 및 성생활 등)는 수집하지 않으며 부득이하게 수집해야
          할 경우 고객들의 사전동의를 반드시 구합니다. 그리고, 어떤 경우에도
          수집한 개인정보를 고객들에게 위 수집·이용목적 이외에 다른 목적으로는
          사용하지 않으며, 고객의 동의를 받은 제3자 이외에는 다른 제3자에게는
          제공하지 않습니다. 나. 개인정보 수집방법 회사는 다음과 같은 방법으로
          개인정보를 수집합니다. - 회원가입, 고객 상담, 경품이벤트 행사진행 등의
          경우에 홈페이지, 서면 양식, 팩스, 전화, 상담 게시판, 이메일, 이벤트
          응모, 배송요청을 통하여 고객이 제공하는 고객정보, 또는 협력회사로부터
          제공되는 고객정보 03. 개인정보의 보유 및 이용 기간 가. 고객이 서비스의
          회원으로서 회사가 제공하는 서비스를 이용하는 동안 회사는 고객들의
          개인정보를 계속적으로 보유하며 서비스 제공 등을 위해 이용합니다. 다만,
          아래의 '06. 고객의 권리와 행사방법' 에서 설명한 절차와 방법에 따라
          고객 본인이 개인정보를 직접 삭제하거나 수정한 경우, 고객이 개인정보의
          삭제나 수정을 요청하는 경우, 고객이 가입 해지를 요청한 경우에는 재생할
          수 없는 방법에 의하여 해당 개인정보를 디스크에서 복원이 불가능한
          방법으로 영구 삭제하며 추후 열람이나 이용이 불가능한 상태로 처리되며,
          고객이 가입 해지후 14일 이내에 삭제됩니다. 또한 상법, 전자상거래
          등에서의 소비자보호에 관한 법률 등 관계법령의 규정에 의하여 보존할
          필요가 있는 경우 회사는 관계법령에서 정한 일정한 기간 동안 개인정보를
          보유 및 이용합니다. 이 경우 회사는 보유하는 개인정보를 그 보유 및
          이용의 목적으로만 이용합니다. 04. 개인정보 파기절차 및 방법 회사는
          귀중한 고객의 개인정보를 안전하게 처리하며, 유출의 방지를 위하여
          다음과 같은 방법과 절차를 통하여 개인정보를 파기합니다. 가. 파기절차 -
          고객이 제공한 정보는 목적이 달성된 후(또는 03. 개인정보의 보유 및
          이용기간 경과) 지체없이 파기되며, 관계법령의 규정에 따라 개인정보를
          파기하지 아니하고 보존하여야 하는 경우에는 해당 개인정보를 다른
          개인정보와 분리해서 별도의 DB(데이터베이스)로 저장・관리된 후
          파기됩니다. - 별도의 DB로 이전된 개인정보는 법률에 의한 경우가
          아니고서는 보유 목적 이외의 다른 목적으로 이용되지 않습니다. 나.
          파기방법 - 전자적 파일 외의 기록물, 인쇄물, 서면 기타 기록매체에
          출력된 개인정보는 분쇄기로 분쇄하거나 소각을 통하여 파기합니다. -
          전자적 파일 형태로 저장된 개인정보는 기록을 재생할 수 없는 기술적
          방법을 사용하여 삭제합니다. 05. 개인정보의 공유 및 제공 가. 회사는
          고객들의 개인정보를 '02. 개인정보의 수집·이용목적, 수집하는 개인정보의
          항목 및 수집방법' 및 '03. 개인정보의 보유 및 이용 기간'에 기재한
          개인정보의 수집목적 및 이용목적의 범위 내에서 사용하며, 고객의 사전
          동의 없이는 동 범위를 초과하여 이용하거나 원칙적으로 고객의 개인정보를
          제3자에게 제공하지 않습니다. 다만, 아래의 경우에는 예외로 합니다. -
          고객의 동의를 받은 경우 - 정보통신서비스의 제공에 관한 계약을 이행하기
          위하여 필요한 개인정보로서 경제적·기술적인 사유로 통상적인 동의를 받는
          것이 뚜렷하게 곤란한 경우 - 정보통신서비스의 제공에 따른 요금정산을
          위하여 필요한 경우 06. 고객의 권리와 행사방법 가. 고객 및 만 14세 미만
          아동인 고객의 법정대리인은 언제든지 등록되어 있는 자신 또는 해당 만
          14세 미만 아동의 개인정보를 조회하거나 수정할 수 있으며 회원탈퇴를
          요청할 수 있습니다. 나. 고객 또는 만 14세 미만 아동인 고객의 개인정보
          조회 및 수정을 위해서는 회사가 운영하는 포켓몬 공식 사이트(이하 "공식
          사이트"라 합니다)에서 ID와 비밀번호를 이용하여 로그인(LOG-IN)하고,
          마이페이지에서 개인정보를 열람할 수 있으며, ID 및 이름을 제외한 모든
          입력사항을 수정할 수 있습니다. 또한, 비밀번호를 잊어버린 경우에는
          'ID/비밀번호 찾기'를 클릭하여 공식 사이트 내의 고객센터의 안내사항에
          따라 본인 확인에 필요한 사항을 입력하면, 본인여부 확인 후 이메일을
          통하여 임시 비밀번호를 알려 드립니다. 다. 회원탈퇴는 공식 사이트의
          '마이페이지'를 클릭하고, '개인정보관리' 중 '회원탈퇴'를 선택한 후
          안내사항에 따라 질문사항 및 본인확인 항목을 입력하고 '회원탈퇴'를
          선택하면, 입력 사항을 기초로 고객 본인여부를 확인한 후 탈퇴를
          처리합니다. 라. 회사는 고객의 요청에 의해 탈퇴 또는 삭제된 개인정보는
          '03. 회사가 수집하는 개인정보의 보유 및 이용기간'에 따라 처리하고 그
          외의 용도로 열람 또는 이용할 수 없도록 처리하고 있습니다. 마. 상기
          방법 외에도 개인정보관리책임자에게 서면, 전화 또는 이메일로 연락하면
          지체 없이 조치하겠습니다. 07. 개인정보 자동 수집 장치의 설치 / 운영 및
          거부에 관한 사항 회사는 회사가 운영하는 온라인 사이트(공식 사이트를
          포함하며 이에 한정되지 않습니다)의 고객들에게 보다 더 향상된 양질의
          서비스를 제공하기 위해서 고객들의 정보를 저장하고 수시로 불러오는
          '쿠키(cookie)'를 사용합니다. 쿠키는 웹사이트를 운영하는데 이용되는
          서버(HTTP)가 고객의 컴퓨터 브라우저에게 보내는 소량의 텍스트 파일의
          정보이며 고객들의 PC 내의 하드디스크에 저장됩니다. 가. 쿠키의 사용목적
          고객들이 서비스에 접속한 후 로그인하여 회원서비스를 이용하기 위해서는
          쿠키를 허용해야 합니다. 회사의 서비스는 고객들에게 적합하고 보다
          유용한 정보를 제공하기 위해서 쿠키를 이용하여 ID에 대한 정보를
          찾아냅니다. 쿠키는 고객의 컴퓨터는 식별하지만 고객을 개인적으로
          식별하지는 않습니다. 쿠키의 저장 정보로는 로그인 키 등이 있으며,
          쿠키를 이용하여 고객들이 방문한 각 서비스와 방문 및 이용형태, 고객
          규모 등을 파악하여 더욱 더 편리한 서비스를 만들어 제공할 수 있고
          고객에게 최적화된 정보를 제공할 수 있습니다. 나. 쿠키의 설치 / 운영 및
          거부 고객들은 쿠키에 대하여 설치 및 사용여부를 선택할 수 있습니다.
          웹브라우저에서 옵션을 설정함으로써 모든 쿠키를 허용할 수도 있고,
          쿠키가 저장될 때마다 확인을 거치거나, 모든 쿠키의 저장을 거부할 수도
          있습니다. 다만, 쿠키의 저장을 거부할 경우에는 로그인이 필요한 일부
          서비스는 이용할 수 없습니다. 08. 개인정보 관련 안전성 확보 조치 회사는
          고객들의 개인정보를 취급함에 있어 개인정보가 분실, 도난, 누출, 변조
          또는 훼손되지 않도록 안전성 확보를 위하여 다음과 같은 기술적 대책을
          강구하고 있습니다. 가. 비밀번호 고객들의 개인정보는 비밀번호에 의해
          보호되고 있습니다. 회원 ID의 비밀번호는 본인만이 알고 있으며,
          개인정보의 확인 및 변경도 비밀번호를 알고 있는 본인에 의해서만
          가능합니다. 따라서 고객은 비밀번호를 누구에게도 알려주어서는 안됩니다.
          이를 위해 회사에서는 기본적으로 PC에서 사용을 마친 다음 온라인상에서
          로그아웃(LOG-OUT) 후, 웹브라우저를 종료하도록 권장합니다. 특히 다른
          사람과 PC를 공유하여 사용하거나 공공장소(회사나 학교, 도서관, 인터넷
          게임방 등)에서 이용한 경우에는 개인정보가 다른 사람에게 알려지는 것을
          막기 위해 위와 같은 절차가 더욱 필요합니다. 나. 해킹 등에 대비한
          대책회사는 해킹이나 컴퓨터 바이러스 등에 의해 회원의 개인정보가
          유출되거나 훼손되는 것을 막기 위해 최선을 다하고 있습니다. 개인정보의
          훼손에 대비해서 자료를 수시로 백업하고 있고, 최신 백신프로그램을
          이용하여 고객들의 개인정보나 자료가 누출되거나 손상되지 않도록
          방지하고 있으며, 암호화 통신 등을 통하여 네트워크상에서 개인정보를
          안전하게 전송할 수 있도록 하고 있습니다. 그리고 침입차단시스템을
          이용하여 외부로부터의 무단 접근을 통제하고 있으며, 기타 시스템적으로
          보안성을 확보하기 위한 가능한 모든 기술적 장치를 갖추려 노력하고
          있습니다. 다. 취급 직원의 최소화 및 교육 회사의 개인정보관련 취급
          직원은 최소한의 인원으로 한정시키고 있고 이를 위한 별도의 비밀번호를
          부여하여 정기적으로 갱신하고 있으며, 담당자에 대한 수시 교육을 통하여
          개인정보 보호정책의 준수를 항상 강조하고 있습니다. 단, 고객 본인의
          부주의나 인터넷상의 문제로 ID, 비밀번호 등 개인정보가 유출해 발생한
          문제에 대해 회사는 일체의 책임을 지지 않습니다. 09. 개인정보 관련
          의견수렴 회사는 개인정보 보호와 관련하여 고객의 의견을 수렴하고 있으며
          불만을 처리하기 위하여 모든 절차와 방법을 마련하고 있습니다. 고객들은
          '12. 개인정보 관리책임자 및 담당자의 소속, 성명 및 연락처'를 참고하여
          전화나 이메일을 통하여 불만사항을 신고할 수 있으며, 회사는 고객들의
          신고사항에 대하여 신속하고도 충분한 답변을 해 드리겠습니다. 그 외에
          개인정보침해에 대하여 신고 또는 상담이 필요한 경우에는, 다음 기관에
          문의하여 주십시오. 개인정보분쟁조정위원회 (http://www.kopico.go.kr,
          1833-6972) 개인정보 침해신고센터 (http://privacy.kisa.or.kr, 118)
          대검찰청 인터넷범죄수사센터 (http://www.spo.go.kr, 02-3480-2000)
          경찰청 사이버안전국 (http://cyberbureau.police.go.kr, 182) 10. 아동의
          개인정보보호 및 법정대리인의 권리와 그 행사방법 현행법상 만 14세
          미만의 아동의 개인정보를 수집 및 이용하고, 수집한 개인정보를 제3자에게
          제공하는 경우에는 사전에 반드시 개인정보의 수집 및 이용, 제3자 제공에
          관한 사항에 대하여 고지하고 법정대리인의 동의를 받아야 합니다. 회사가
          수집한 어떠한 개인정보에 대해서도 만 14세 미만 아동의 법정대리인은 위
          아동의 개인정보의 열람, 정정, 동의철회를 요청할 수 있으며, 이러한
          요청이 있을 경우 회사는 지체 없이 필요한 조치를 취합니다. 11. 개인정보
          보호책임자 및 담당자의 소속, 성명 및 연락처 회사는 고객이 좋은 정보를
          안전하게 이용할 수 있도록 최선을 다하고 있습니다. 개인정보를
          보호하는데 있어 고객에게 고지한 사항들에 반하는 사고가 발생할 경우
          개인정보 보호책임자가 책임을 집니다. 고객 개인정보와 관련한 ID의
          비밀번호에 대한 보안유지 책임은 해당 고객 자신에게 있습니다. 회사는
          비밀번호에 대해 어떠한 방법으로도 고객에게 직접 질문하는 경우는
          없으므로 타인에게 비밀번호가 유출되지 않도록 각별히 주의 바랍니다.
          특히 '09. 개인정보 관련 기술/관리적 대책' 에서 명시한 것과 같이
          공공장소에서 로그인한 경우에는 더욱 유의해야 합니다. 회사는 개인정보에
          대한 의견수렴 및 불만처리를 담당하는 개인정보 보호책임자 및 담당자를
          지정하고 있고, 연락처는 아래와 같습니다. [개인정보 보호책임자] 이 름 :
          이소은 소속 / 직위 : ㈜🍀JOY_FARM /대표이사 이 메 일 :
          Soeun318@joyfarm.co.kr [개인정보 관리담당자] 이름 : 강태현 소속 / 직위
          : ㈜🍀JOY_FARM /부장 / 이메일:kth232@joyfarm.com [회원 페이지 담당자 ]
          이름: 유준범 소속 / 직위 : ㈜🍀JOY_FARM /과장 / 이메일 :
          byby5546@joyfarm.com [메인 담당자] 이름: 최시원(퇴사) 소속/직위 :
          ㈜🍀JOY_FARM / 차장 / 이메일 : siwon77@joyfarm.com [게시판 담장자]
          이름: 양예지 소속/직위: ㈜🍀JOY_FARM / 주임 / 이메일 :
          yeji219@joyfarm.com [예약 관리 담장자] 이름: 이진표 소속/직위 :
          ㈜🍀JOY_FARM / 대리 이메일 : jinpyo717@joyfarm.com [축제,이벤트
          담당자] 이름: 서정현 소속/직위 : ㈜🍀JOY_FARM / 주임 이메일 :
          jeongh119@joyfarm.com 12. 고지의 의무 현 '개인정보처리방침'의 내용에
          추가, 삭제 및 수정이 있을 시에는 회사의 홈페이지 첫 화면의 '공지사항'
          또는 별도의 창을 통해 고지합니다. 공고 일자 : 2024년 9월 1일 시행 일자
          : 2024년 8월 1일
        </textarea>

        <div className="terms-agree" onClick={onToggle}>
          {form.agree ? <FaCheckSquare /> : <FaRegCheckSquare />}
          {t('회원가입_약관에_동의합니다')}

          <MessageBox color="danger" messages={errors.agree} />
        </div>

        <StyledButtons>
          <button type="button" onClick={onReset} gid={form.gid}>
            {t('다시입력')}
          </button>
          <button type="submit">{t('가입하기')}</button>
        </StyledButtons>
      </div>
    </FormBox>
  );
};

export default React.memo(JoinForm);
