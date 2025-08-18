@import org.example.hexlet.dto.courses.CoursesPage
@param CoursesPage page

@template.layout.page(
    content = @`
        <h1>${page.getHeader()}</h1>
        @if(page.getCourses().isEmpty())
            <p>No courses found matching your search criteria</p>
        @else
            @for(var course : page.getCourses())
               <div>
                   <h2><a href="/courses/${course.getId()}">${course.getName()}</a></h2>
               </div>
            @endfor
        @endif
        <a href="/courses/build">Add course</a>
    `,
    page = page
)
