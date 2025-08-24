@import org.example.hexlet.dto.courses.CoursePage
@param CoursePage page

@template.layout.page(
    content = @`
        <h1>${page.getCourse().getName()}</h1>
        <p>${page.getCourse().getDescription()}</p>

        <div>
            <a href="/courses/${page.getCourse().getId()}/edit">Edit</a>
            <form action="/courses/${page.getCourse().getId()}/delete" method="post" style="display:inline">
                <button type="submit" class="btn btn-danger">Delete Course</button>
            </form>
        </div>

        <p><a href="/courses">Back to all courses</a></p>
    `,
    coursesPage = null,
    usersPage = null,
    page = page
)
