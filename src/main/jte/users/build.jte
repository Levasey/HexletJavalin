@template.layout.page(
    content = @`
        <h1>Register New User</h1>

        <form action="/users" method="post">
            <div>
                <label for="name">Name:</label>
                <input type="text" id="name" name="name" required>
            </div>
            <div>
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" required>
            </div>
            <div>
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" required>
            </div>
            <div>
                <label for="passwordConfirmation">Password Confirmation:</label>
                <input type="password" id="passwordConfirmation" name="passwordConfirmation" required>
            </div>
            <button type="submit">Register</button>
        </form>

        <p><a href="/users">Back to users</a></p>
    `
)