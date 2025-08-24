@import org.example.hexlet.dto.courses.CoursesPage
@param CoursesPage page

@template.layout.page(
    content = @`
        <h1>${page.getHeader() != null ? page.getHeader() : "All Courses"}</h1>

        @if(page.getTerm() != null && !page.getTerm().isEmpty())
            <p>Search results for: "${page.getTerm()}"</p>
        @endif

        @if(page.getCourses().isEmpty())
                <p>No courses found</p>
        @else
            @for(var course : page.getCourses())
                <p><a href="/courses/${course.getId()}">${course.getName()}</a></p>
            @endfor
        @endif

        <p><a href="/courses/build">Create New Course</a></p>
    `,
    coursesPage = page,
    usersPage = null,
    page = page
)
