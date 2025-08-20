@template.layout.page(
    content = @`
        <h1>Create New Course</h1>

        <form action="/courses" method="post">
            <div>
                <label for="name">Name:</label>
                <input type="text" id="name" name="name" required>
            </div>
            <div>
                <label for="description">Description:</label>
                <textarea id="description" name="description" required></textarea>
            </div>
            <button type="submit">Create Course</button>
        </form>

        <p><a href="/courses">Back to courses</a></p>
    `,
    coursesPage = null,
    usersPage = null
)