# be18-2nd-YuGuanZhang-Lumi

# Lumi

# 1. 팀원 소개

<table>
  <tr align="center">
    <td>김대의</td>
    <td>윤동기</td>
    <td>조용주</td>
  </tr>
  <tr align="center">
    <td><a target="_blank" href="https://github.com/kimeodml"><img src="https://avatars.githubusercontent.com/u/88065770?v=4" width="100px"><br>@kimeodml</a></td>
    <td><a target="_blank" href="https://github.com/ydg010"><img src="https://avatars.githubusercontent.com/u/97106031?v=4" width="100px"><br>@ydg010</a> </td>
    <td><a target="_blank" href="https://github.com/whwjyj"><img src="https://avatars.githubusercontent.com/u/138665848?v=4" width="100px"><br>@whwjyj</a> </td>
  </tr>
</table>

# 2. 기술 스택
# 3. 서비스 소개
## 3-1. 배경
### 기존 과외 플랫폼의 문제점
1. **과외 과정에서 필요한 관리와 소통을 지원하는 기능의 부족**
    
    <img width="1346" height="648" alt="image" src="https://github.com/user-attachments/assets/e045736e-fce1-4d3a-aaf3-8521521a819f" />

    
    (출처: https://www.donga.com/news/Culture/article/all/20250409/131379599/1)
    
    (출처: https://biz.heraldcorp.com/article/10002626)
    
    기존 온라인 과외 플랫폼들은 *멘토와 학생을 연결해 주는 매칭 기능*에 집중하여, 학부모가 원하는 과목과 시간대에 맞춰 멘토를 추천하는 데는 효율적입니다. 하지만 매칭 이후의 학생 학습 현황이나 멘토링 피드백을 관리하는 시스템은 부족합니다.
    
2. **학습 자료의 분산**
    
    실제 과외 환경에서는 수업 내용 정리, 과제 제출과 피드백, 시험 대비, 성적 관리 등 다양한 활동이 이루어집니다. 하지만 이를 체계적으로 기록하고 공유할 수 있는 도구가 없다 보니, 멘토와 학생은 단순 메신저나 오프라인 기록에 의존하는 경우가 많습니다. 이로 인해 학습 자료가 흩어지고, 학습 진척 상황을 한눈에 확인하기 어려우며, 체계적인 관리가 어렵습니다.
    
3. **학부모의 과도한 개입**
    
    <img width="1344" height="330" alt="image" src="https://github.com/user-attachments/assets/e7e73b44-4d56-45d6-9fc9-18dd1f2bcc87" />

    
    (출처: https://www.nocutnews.co.kr/news/5983805)
    
    학부모는 자녀의 학습 현황을 잘 알지 못하기 때문에 작은 진척 상황도 멘토에게 직접 묻거나, 수업 방식에 대해 불필요한 간섭을 하게 됩니다. 이는 멘토의 수업 자율성을 해치고, 학생에게도 불필요한 압박감을 주게 됩니다. 예를 들어, 한 학부모가 “이번 주에 무슨 내용을 가르쳤는지, 숙제는 다 했는지, 시험 준비는 잘 되고 있는지”를 매번 직접 확인하려고 할 때, 멘토는 본질적인 수업보다 보고와 설명에 더 많은 시간을 빼앗기게 됩니다.
    

저희 서비스는 이러한 문제를 해결하기 위해 만들어졌습니다.

단순히 멘토와 학생을 연결해 주는 역할에 머무르지 않고, **멘토–학생 간의 원활한 과외를 위한 협업 환경**을 제공합니다. 일종의 *과외 전용 노션, 디스코드*같은 공간을 제공하는 것을 지향합니다.

저희의 서비스를 통해 학부모는 자녀의 과제 제출 여부와 성적 변화를 직접 확인할 수 있고,  멘토는 더 이상 일일이 답변하거나 보고서를 작성할 필요가 없고, 학생은 과제와 수업 내용이 한곳에 정리되어 있어 학습 효율이 크게 높아집니다.
## 3-2. 소개
**LUMI**는 과외를 보다 효율적이고 체계적으로 운영할 수 있도록 설계된 **과외 전용 협업 플랫폼**입니다.

기존 과외 플랫폼이 주로 멘토와 학생 매칭에 초점을 맞춘 것과 달리, 본 서비스는 **매칭 이후 실제 수업 과정과 학습 관리를 지원**하는 데 집중합니다.


### 핵심 기능 및 특징

1. **멘토 중심의 관리 기능**
    - 멘토는 채널 단위로 학생을 관리하며, 수업 자료를 업로드하고 과제를 부여할 수 있습니다.
    - 제출 현황, 피드백, 평가 결과를 한 곳에서 확인하며, 수업 운영에 필요한 모든 기록을 체계적으로 관리할 수 있습니다.
2. **학생 중심의 학습 공간**
    - 학생은 본인의 채널에서 과제 제출, 수업 자료 열람, 피드백 확인 등을 수행하며 학습 진행 상황을 한눈에 파악할 수 있습니다.
    - 이를 통해 학생은 과외 활동에 집중하면서 자기주도 학습을 경험할 수 있습니다.
3. **학부모를 위한 객관적 정보 제공**
    - 과제 제출 기록, 수업 자료, 시험 성적, 평가 결과 등 투명한 지표를 학부모가 직접 확인할 수 있습니다.
    - 학부모는 멘토에게 불필요하게 연락하거나 간섭할 필요가 없으며, 데이터를 기반으로 학습 진행 상황을 신뢰할 수 있습니다.
    - 이를 통해 멘토의 자율성이 보장되고, 학부모는 필요한 정보를 충분히 얻으면서도 과외 운영에 방해되지 않습니다.
4. **협업과 소통의 최적화**
    - 수업 자료, 과제, 피드백, 일정 등 과외 전반을 한 공간에서 관리할 수 있어, 멘토와 학생 간 소통이 자연스럽고 효율적입니다.
    - 학부모 역시 필요할 때만 정보를 확인하며, 과외 과정에 직접 개입하지 않도록 설계되어 있습니다.


### 서비스 지향점

**LUMI**는 단순한 과외 관리 툴이 아니라, **과외 전용 디지털 협업 공간**입니다.

멘토, 학생, 학부모 간 역할과 정보 흐름을 명확히 하여 과외 전반을 독립적이고 효율적으로 운영할 수 있는 통합 플랫폼을 지향합니다. 

멘토는 수업 준비와 지도에 온전히 집중할 수 있고, 

학생은 자신의 학습 진행 상황과 피드백을 명확히 확인하며 자기주도적으로 학습을 관리할 수 있습니다. 

학부모는 객관적인 데이터를 통해 자녀의 학습 상황을 신뢰하며 확인할 수 있어, 불필요한 개입 없이도 안심할 수 있습니다.

결국, **LUMI**의 궁극적인 목표는 멘토와 학생 중심의 학습 자율성을 보장하고, 학부모에게는 투명한 정보를 제공하며, 과외 전반을 체계적으로 운영할 수 있는 **과외 전용 디지털 협업 공간**을 실현하는 것입니다.
# 4. 기획

<details>
  <summary>요구사항 명세서</summary>
  <ul>
    <li><a href="https://docs.google.com/spreadsheets/d/1hU3ODNUjGjJ8DMJBtgb53_1PkK_MNOq-_kNV6GPlQLo/edit?gid=0#gid=0" target="_blank">요구사항 명세서</a></li>
  </ul>
  <img width="1084" height="631" alt="image" src="https://github.com/user-attachments/assets/dfedcf8c-146f-4859-8d18-5bde531edbbb" />
</details>

<details>
  <summary>ERD</summary>
  <ul>
    <li><a href="https://www.erdcloud.com/d/BymTsRnEXr4jd5JFp" target="_blank">ERD</a></li>
  </ul>
  <img width="1465" height="820" alt="image" src="https://github.com/user-attachments/assets/61cf47b3-bfdd-4884-b635-829b973e21ce" />

  
</details>

<details>
  <summary>테이블 명세서</summary>
  <ul>
    <li><a href="https://docs.google.com/spreadsheets/d/1hU3ODNUjGjJ8DMJBtgb53_1PkK_MNOq-_kNV6GPlQLo/edit?gid=561737687#gid=561737687" target="_blank">테이블 명세서</a></li>
  </ul>
  <img width="1343" height="679" alt="스크린샷 2025-08-14 오후 3 58 21" src="https://github.com/user-attachments/assets/61ef2523-ddc1-47f7-b745-75e50706f3ea" />

</details>

<details>
  <summary>API 명세서</summary>
  <ul>
    <li><a href="https://goldenrod-wildebeest-a18.notion.site/API-2333689564888188814febb1f26786c9?source=copy_link" target="_blank">API 명세서</a></li>
  </ul>
</details>


<details>
  <summary>테스트 계획 및 결과 보고서</summary>
  <details>
    <summary>로그인</summary>
    <ul>
      <li> <img width="851" height="536" alt="스크린샷 2025-09-25 오전 12 28 28" src="https://github.com/user-attachments/assets/8c1fdbaf-30d6-420f-a14f-76d6a5e3bfb9" /></li>
    </ul>
  </details>
</details>

# 5. 시스템 아키텍처 및 기술적 특징
# 6. 회고록
