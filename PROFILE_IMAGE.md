# 프로필 이미지 업로드
## 프론트앤드 - 리액트

### 파일 업로드 공통 모듈 추가
> src/commons/components/FileUpload.js

```jsx
import React, { useCallback, useState } from 'react';
import apiRequest from '../libs/apiRequest';
import { SmallButton } from './Buttons';
import MessageBox from './MessageBox';
import { useTranslation } from 'react-i18next';

const FileUpload = ({
  children,
  gid,
  location,
  color,
  imageOnly,
  single,
  done,
  callback,
  width,
}) => {
  const [message, setMessage] = useState('');

  const { t } = useTranslation();

  // 버튼 클릭 처리
  const onButtonClick = useCallback(() => {
    const fileEl = document.createElement('input');
    fileEl.type = 'file';
    fileEl.multiple = !Boolean(single);
    fileEl.click();

    const fileListener = (e) => {
      const files = e.target.files;
      try {
        if (files.length === 0) {
          throw new Error(t('파일을_선택하세요.'));
        }

        if (imageOnly) {
          for (const file of files) {
            if (!file.type.includes('image/')) {
              throw new Error(t('이미지_형식의_파일만_업로드하세요.'));
            }
          }
        }

        if (!gid?.trim()) {
          throw new Error(t('필수항목(gid)_누락'));
        }

        const formData = new FormData();
        formData.append('gid', gid.trim());
        if (location) formData.append('location', location);

        for (const file of files) {
          formData.append('file', file);
        }

        if (single) {
          formData.append('single', Boolean(single));
        }

        if (imageOnly) {
          formData.append('imageOnly', Boolean(imageOnly));
        }

        if (done) {
          formData.append('done', Boolean(done));
        }

        (async () => {
          try {
            const res = await apiRequest('/file/upload', 'POST', formData);
            if (res.status === 201 && res.data.success) {
              // 파일 업로드 후속 처리
              if (typeof callback === 'function') {
                callback(res.data.data);
              }
              // 성공시 처리
              return;
            }

            if (res.data.message) setMessage(res.data.message);
          } catch (err) {
            setMessage(err.message);
            console.error(err);
          }
        })();
      } catch (err) {
        setMessage(err.message);
      }
    };

    fileEl.removeEventListener('change', fileListener);

    fileEl.addEventListener('change', fileListener);
  }, [single, gid, location, imageOnly, t, callback, done]);

  return (
    <>
      <SmallButton
        width={width}
        type="button"
        color={color}
        onClick={onButtonClick}
      >
        {children}
      </SmallButton>
      {message && <MessageBox color="danger">{message}</MessageBox>}
    </>
  );
};

export default React.memo(FileUpload);
```

### 회원 가입에 적용

> src/member/containers/JoinContainer.js

```jsx
import React, { useState, useCallback } from 'react';

...

import JoinForm from '../components/JoinForm';
import apiRequest from '../../commons/libs/apiRequest';


const JoinContainer = () => {
  // 양식 데이터
  const [form, setForm] = useState({
    gid: '' + Date.now(),
    agree: false,
  });
  
  ...
  
  // 파일 업로드 콜백 처리
  const fileUploadCallback = useCallback((files) => {
    // 프로필 파일 정보 업데이트
    if (files.length === 0) return;
    setForm((form) => ({ ...form, profile: files[0] }));
  }, []);

  const fileDeleteCallback = useCallback(
    (seq) => {
      if (!window.confirm(t('정말_삭제하시겠습니까?'))) {
        return;
      }

      (async () => {
        try {
          const res = await apiRequest(`/file/delete/${seq}`, 'DELETE');
          if (res.status === 200 && res.data.success) {
            setForm((form) => ({ ...form, profile: null }));
            return;
          }

          if (res.data.message) {
            setErrors({ global: [res.data.message] });
          }
        } catch (err) {
          setErrors({ global: [err.message] });
          console.error(err);
        }
      })();
    },
    [t],
  );
  
  return (
    <JoinForm
      form={form}
      errors={errors}
      onSubmit={onSubmit}
      onChange={onChange}
      onToggle={onToggle}
      onReset={onReset}
      fileUploadCallback={fileUploadCallback}
      fileDeleteCallback={fileDeleteCallback}
    />
  );
};

export default React.memo(JoinContainer);
```

### 프로필 이미지 출력 컴포넌트 추가

> src/member/components/ProfileImage.js

```jsx
import React, { useCallback, useState } from 'react';
import styled, { css } from 'styled-components';
import { AiFillCloseSquare } from 'react-icons/ai';
import apiRequest from '../../commons/libs/apiRequest';

const ImageBox = styled.div`
  width: ${({ width }) => width ?? '100%'};
  height: ${({ height }) => height ?? '300px'};
  ${({ radius }) => css`
    border-radius: ${radius};
  `}
  overflow: hidden;
  margin-bottom: 10px;
  position: relative;

  .image {
    width: 100%;
    height: 100%;
    background: url('${({ url }) => url}') no-repeat center center;
    background-size: cover;

    cursor: pointer;
  }

  .icon {
    position: absolute;
    top: 2px;
    right: 2px;
    font-size: 2.5rem;
    color: #fff;
    cursor: pointer;
  }
`;

const ProfileImage = ({ items, width, height, radius, onDelete }) => {
  items = Array.isArray(items) ? items : [items];
  
  return (
    <>
      {items?.length > 0 &&
        items.map((item) => (
          <ImageBox
            key={item.seq}
            className="inner"
            url={item.fileUrl}
            width={width}
            height={height}
            radius={radius}
          >
            <AiFillCloseSquare
              className="icon"
              onClick={() => onDelete(item.seq)}
            />
            <div className="image"></div>
          </ImageBox>
        ))}
    </>
  );
};

export default React.memo(ProfileImage);
```

### 회원가입 양식에 반영

> src/member/components/JoinForm.js

```jsx
import React from 'react';

... 

import MessageBox from '../../commons/components/MessageBox';
import FileUpload from '../../commons/components/FileUpload';
import ProfileImage from './ProfileImage';

...

const JoinForm = ({
  form,
  onSubmit,
  onChange,
  onToggle,
  onReset,
  errors,
  fileUploadCallback,
  fileDeleteCallback,
}) => {
 const { t } = useTranslation();
  return (
    <FormBox autoComplete="off" onSubmit={onSubmit}>
       
       ...
        
      <dl>
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
            {t('이미지_업로드')}
          </FileUpload>
        </dd>
      </dl>
      
      ...
      
    </FormBox>  
  );
};

export default React.memo(JoinForm);
```


## 백앤드 - API-SERVICE

### file 업로드 경로 설정
- file 업로드 경로 (/api/v1/file/\**, /api/v1/upload/\**) 모두 허용으로 변경

> global/configs/SecurityConfig.java

```java
...
public class SecurityConfig {
    ...

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(c -> c.disable())
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(h -> {
                    h.authenticationEntryPoint((req, res, e) -> res.sendError(HttpStatus.UNAUTHORIZED.value()));
                    h.accessDeniedHandler((req, res, e) -> res.sendError(HttpStatus.UNAUTHORIZED.value()));
                })
                .authorizeHttpRequests(c -> {
                    c.requestMatchers(
                                    "/account",
                                    "/account/token",
                                    "/file/**",
                                    "/upload/**",
                                    "/restaurant/**",
                                    "/tour/**"
                            ).permitAll() // 회원가입, 로그인(토큰)은 모든 접근 가능
                            .anyRequest().authenticated(); // 그외에는 인증 필요
                });
        
        ...
        
    }
    
    ...
}
```

### Member엔티티에 profile 항목 추가 

> member/entities/Member.java

```java
...

public class Member extends BaseEntity {
    ...

    @Transient
    private FileInfo profile;
}
```

### 커맨드 객체에 profile 항목 처리 

- profile 항목이 프론트앤드에서 전달되더라도 JSON 변환 무시 처리
- @JsonIgnoreProperties(ignoreUnknown = true) 추가

> member/controllers/RequestJoin.java

```java
...

@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestJoin {
    ...   
}
```

### 회원 추가 및 수정시 gid로 파일 업로드 완료 처리

> member/services/MemberSaveService.java 

```java
...
public class MemberSaveService {
    
    ...
    
    private final PasswordEncoder passwordEncoder;
    private final FileUploadDoneService doneService;
    
    ...

    public void save(Member member, List<Authority> authorities) {
        ...

        memberRepository.saveAndFlush(member);

        doneService.process(gid); // 파일 업로드 완료 처리 
        
        ...
    }
}
```

### 회원정보 조회시 프로필 이미지 포함처리

> member/services/MemberInfoService.java

```java
...

public class MemberInfoService implements UserDetailsService {
   
    ...

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ...

        // 프로필 이미지 처리
        List<FileInfo> files = fileInfoService.getList(member.getGid());
        if (files != null && !files.isEmpty()) member.setProfile(files.get(0));

        return MemberInfo.builder()
                .email(member.getEmail())
                .password(member.getPassword())
                .member(member)
                .authorities(authorities)
                .build();
    }
}
```
