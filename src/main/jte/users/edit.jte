@import org.example.hexlet.dto.users.UserPage
@param UserPage page

@template.layout.page(
    content = @`
        <h1>Edit User Profile</h1>

        <form action="/users/${page.getUser().getId()}" method="post">
            <input type="hidden" name="_method" value="PATCH">
            <div>
                <label for="name">Name:</label>
                <input type="text" id="name" name="name" value="${page.getUser().getName()}" required>
            </div>
            <div>
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" value="${page.getUser().getEmail()}" required>
            </div>
            <div>
                <label for="password">New Password (leave blank to keep current):</label>
                <input type="password" id="password" name="password">
            </div>
            <button type="submit">Update Profile</button>
        </form>

        <p><a href="/users/${page.getUser().getId()}">Cancel</a></p>
    `
)
