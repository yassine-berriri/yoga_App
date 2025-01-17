describe('Account spec', () => {
    beforeEach(()=> {
        cy.intercept({method: 'GET', url: '/api/session'},[]).as('sessions')
        
        cy.adminLogin();
    })

    it('Account admin successfull', () => {
        cy.fixture('adminUser').then((admin) => {
            cy.intercept({method: 'GET', url: '/api/user/1'}, admin).as('adminUser')

            cy.contains('span', 'Account').click()

            cy.get('.my2').contains('You are admin')
        })
    })

});