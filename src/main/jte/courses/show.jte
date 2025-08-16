@import org.example.hexlet.dto.courses.CoursePage
@param CoursePage page

@template.layout.page(
    content = @`
        <h1>Описание курса ${page.getCourse().getName()}</h1>
        <p>${page.getCourse().getDescription()}</p>
        <p><a href="/courses">← Назад к списку курсов</a></p>
    `
)