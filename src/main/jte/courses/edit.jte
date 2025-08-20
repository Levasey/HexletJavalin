@import org.example.hexlet.dto.courses.CoursePage
@param CoursePage page

@template.layout.page(
    content = @`
        <h1>Edit Course</h1>

        <form action="/courses/${page.getCourse().getId()}" method="post">
            <input type="hidden" name="_method" value="PATCH">
            <div>
                <label for="name">Name:</label>
                <input type="text" id="name" name="name" value="${page.getCourse().getName()}" required>
            </div>
            <div>
                <label for="description">Description:</label>
                <textarea id="description" name="description" required>${page.getCourse().getDescription()}</textarea>
            </div>
            <button type="submit">Update Course</button>
        </form>

        <p><a href="/courses/${page.getCourse().getId()}">Cancel</a></p>
    `,
    coursesPage = null,
    usersPage = null
)