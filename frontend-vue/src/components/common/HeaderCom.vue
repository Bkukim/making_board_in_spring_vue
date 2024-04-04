// 머리말

<template>
  <div>
    <!-- 부트스트랩 메뉴 넣기 -->
    <nav class="navbar navbar-expand-lg  navbar-dark bg-dark">
      <div class="container-fluid">
        <a class="navbar-brand" href="#">Simple</a>
        <button
          class="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarSupportedContent"
          aria-controls="navbarSupportedContent"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
          <ul class="navbar-nav me-auto mb-2 mb-lg-0">
            <li class="nav-item">
              <a class="nav-link active" aria-current="page" href="#">Home</a>
            </li>
            <!-- 드롭다운 메뉴 -->
            <li class="nav-item dropdown">
              <!-- 대메뉴 -->
              <a
                class="nav-link dropdown-toggle"
                href="#"
                role="button"
                data-bs-toggle="dropdown"
                aria-expanded="false"
              >
                기초 예제
              </a>
              <!-- 소메뉴 -->
              <ul class="dropdown-menu">
                <li><a class="dropdown-item" href="/dept">Dept</a></li>
                <li><a class="dropdown-item" href="/add-dept">Add Dept</a></li>
                <li>
                  <hr class="dropdown-divider" />
                  <!-- 구분선 -->
                </li>
                <li><a class="dropdown-item" href="/emp">Emp </a></li>
                <li><a class="dropdown-item" href="/add-emp">Add Emp</a></li>
              </ul>
            </li>
            <li class="nav-item dropdown">
              <!-- 대메뉴 -->
              <a
                class="nav-link dropdown-toggle"
                href="#"
                role="button"
                data-bs-toggle="dropdown"
                aria-expanded="false"
              >
                심화 예제
              </a>
              <!-- 소메뉴 -->
              <ul class="dropdown-menu">
                <li><a class="dropdown-item" href="/fileDb">FileDb</a></li>
                <li><a class="dropdown-item" href="/add-fileDb">Add FileDb</a></li>
                <li>
                  <hr class="dropdown-divider" />
                  <!-- 구분선 -->
                </li>
                <li><a class="dropdown-item" href="/gallery">Gallery </a></li>
                <li><a class="dropdown-item" href="/add-gallery">Add Gallery</a></li>
              </ul>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  </div>
</template>
<script></script>
<style></style>
