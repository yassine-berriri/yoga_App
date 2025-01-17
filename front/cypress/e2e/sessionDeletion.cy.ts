describe('Session Deletion spec', () => {
    
    beforeEach(()=> {
        cy.fixture('sessions').then((sessions) => {
            cy.intercept({method: 'GET', url: '/api/session'}, sessions).as('sessions')
        })
    
        cy.fixture('session').then((session) => {
            cy.intercept({method: 'GET', url: 'api/session/2'}, session).as('session')
        })
        
        cy.fixture('teachers').then((trachers) => {
            cy.intercept({method: 'GET', url: '/api/teacher'}, trachers).as('teachers');
        })
    
        cy.adminLogin();
    })

    it('Session deleted successfully', () => {
        cy.contains('span', 'Detail').click();

        cy.url().should('include', '/sessions/detail/2')

        cy.intercept({method: 'DELETE', url: '/api/session/2',}, {statusCode: 200}).as('deletedSession')

        cy.contains('span', 'Delete').click();

        cy.url().should('include', '/sessions')
        cy.get('.mat-simple-snack-bar-content').contains("Session deleted !")
    });
  
})