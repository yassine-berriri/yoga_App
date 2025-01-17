describe('Logout spec', () => {
    beforeEach(() => {
        cy.fixture('sessions').then((sessions) => {
            cy.intercept({method: 'GET', url: '/api/session'},sessions).as('sessions')
        })

        cy.login();



    });

    it('Logout successfull', () => {
        cy.contains('span', 'Logout').click()

        cy.url().should('include', '')

    });

  
});