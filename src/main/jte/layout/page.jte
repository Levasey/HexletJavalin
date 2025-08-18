@import gg.jte.Content
@import org.example.hexlet.dto.courses.CoursesPage

@param Content content
@param CoursesPage page = null

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hexlet App</title>
    <style>
        body { font-family: Arial, sans-serif; line-height: 1.6; max-width: 800px; margin: 0 auto; padding: 20px; }
        header { border-bottom: 1px solid #eee; padding-bottom: 10px; margin-bottom: 20px; }
        footer { border-top: 1px solid #eee; padding-top: 10px; margin-top: 20px; font-size: 0.9em; }
        nav a { margin-right: 15px; text-decoration: none; }
        .search-form { display: inline-block; margin-left: 20px; }
    </style>
</head>
<body>
    <header>
        <nav>
            <a href="/">Главная</a>
            <a href="/courses">Курсы</a>
            <a href="/users/build">Регистрация</a>
            @if (page != null)
                <!-- Only show search if we have a CoursesPage -->
                <div class="search-form">
                    <form action="/courses" method="get">
                        <input type="search" name="term" value="${page.getTerm() != null ? page.getTerm() : ""}" />
                        <input type="submit" value="Search" />
                    </form>
                </div>
            @endif
        </nav>
    </header>

    <main>
        ${content}
    </main>

    <footer>
        <p>© 2025 Hexlet Project. All rights reserved.</p>
    </footer>
</body>
</html>