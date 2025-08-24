@import gg.jte.Content
@import org.example.hexlet.dto.courses.CoursesPage
@import org.example.hexlet.dto.users.UsersPage
@import org.example.hexlet.dto.BasePage

@param Content content
@param CoursesPage coursesPage = null
@param UsersPage usersPage = null
@param String flashMessage = null
@param String flashType = null
@param BasePage page = null

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hexlet App</title>
    <link rel="icon" href="data:,"> <!-- Empty favicon to prevent 404 -->
    <link rel="stylesheet" href="C:\Users\User\IdeaProjects\HexletJavalin\src\main\jte\style.css">
</head>
<body>
    <header>
        <div class="container">
            <nav>
                <div class="nav-links">
                    <a href="/">Главная</a>
                    <a href="/courses">Курсы</a>
                    <a href="/users">Пользователи</a>
                    <a href="/users/build">Регистрация</a>
                </div>

                @if (coursesPage != null)
                    <div class="search-form">
                        <form action="/courses" method="get">
                            <input type="search" name="term" value="${coursesPage.getTerm() != null ? coursesPage.getTerm() : ""}" placeholder="Search courses...">
                            <input type="submit" value="Search">
                        </form>
                    </div>
                @endif

                @if (usersPage != null)
                    <div class="search-form">
                        <form action="/users" method="get">
                            <input type="search" name="term" value="${usersPage.getTerm() != null ? usersPage.getTerm() : ""}" placeholder="Search users...">
                            <button type="submit">Search</button>
                        </form>
                    </div>
                @endif
            </nav>
        </div>
    </header>
    <main>
        <div class="container">
            <div class="content-wrapper">
                @if (page != null && page.hasFlash())
                    <div class="flash flash-${page.getFlashType() != null ? page.getFlashType() : "info"}">
                        ${page.getFlash()}
                    </div>
                @endif

                ${content}
            </div>
        </div>
    </main>

    <footer>
        <div class="container">
            <p>© 2025 Hexlet Project. All rights reserved.</p>
        </div>
    </footer>
</body>
</html>
